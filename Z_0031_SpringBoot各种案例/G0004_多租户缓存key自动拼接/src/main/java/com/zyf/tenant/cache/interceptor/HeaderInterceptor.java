package com.zyf.tenant.cache.interceptor;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zyf.tenant.cache.config.tenant.TenantConfigProperties;
import com.zyf.tenant.cache.constant.FeignConstants;
import com.zyf.tenant.cache.constant.RedisConstants;
import com.zyf.tenant.cache.constant.SecurityConstants;
import com.zyf.tenant.cache.entity.LoginUser;
import com.zyf.tenant.cache.entity.Tenant;
import com.zyf.tenant.cache.entity.UserEntity;
import com.zyf.tenant.cache.tenant.TenantContextHolder;
import com.zyf.tenant.cache.utils.SecurityContextHolder;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 自定义请求头拦截器，将Header数据封装到线程变量中方便获取
 * 注意：此拦截器会同时验证当前用户有效期自动刷新有效期
 */
@Slf4j
@Component
public class HeaderInterceptor implements AsyncHandlerInterceptor {

    @Autowired
    private TenantConfigProperties tenantConfigProperties;

    @Autowired
    private ObjectMapper objectMapper;

    private final static String UNDEFINED_STR = "undefined";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        String isIgnoreWhite = request.getHeader(FeignConstants.IS_IGNORE_WHITE);
        String fromSource = request.getHeader(FeignConstants.FROM_SOURCE);
        String headerTenantId = request.getHeader(RedisConstants.TENANT_ID);
        String paramTenantId = request.getParameter(RedisConstants.TENANT_ID);

        log.debug("获取header中的租户ID为:{}", headerTenantId);

        if (!tenantConfigProperties.isEnable()) {
            TenantContextHolder.setTenantId(RedisConstants.TENANT_ID_1);
        } else if (StrUtil.isNotBlank(headerTenantId) && !StrUtil.equals(UNDEFINED_STR, headerTenantId)) {
            TenantContextHolder.setTenantId(headerTenantId);
        } else if (StrUtil.isNotBlank(paramTenantId) && !StrUtil.equals(UNDEFINED_STR, paramTenantId)) {
            TenantContextHolder.setTenantId(paramTenantId);
        }

        if (tenantConfigProperties.isEnable()) {
            String url = request.getRequestURI();
            // 跳过不需要验证的路径
            if (TenantContextHolder.matches(url)) {
                isIgnoreWhite = "1";
            }
        }

        String token = SecurityContextHolder.getToken();
        if (StringUtils.isNotEmpty(token)) {
            LoginUser loginUser = SecurityContextHolder.getLoginUser(token);
            if (loginUser != null) {
                if (tenantConfigProperties.isEnable()) {
                    if (StrUtil.equalsIgnoreCase(fromSource, FeignConstants.INNER)
                            || StrUtil.equals(isIgnoreWhite, "1")) {
                        return true;
                    }
                    // is admin
                    if (StrUtil.equals(loginUser.getUserId(), "1")) {
                        return true;
                    }

                    final List<Tenant> tenants = loginUser.getTenants();
                    if (CollUtil.isNotEmpty(tenants)) {
                        final String tenantId = TenantContextHolder.getTenantId();
                        final List<String> tenantIds = tenants.stream().map(Tenant::getTenantId).toList();
                        if (!tenantIds.contains(tenantId)) {
                            returnJson(response, 408, "租户标识与用户不匹配");
                            return false;
                        }
                    }
                }
            }

            if (tenantConfigProperties.isEnable()) {
                if (StrUtil.equalsIgnoreCase(fromSource, FeignConstants.INNER)
                        || StrUtil.equals(isIgnoreWhite, "1")) {
                    return true;
                }
            }

        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        TenantContextHolder.clear();
        SecurityContextHolder.remove();
    }


    private void returnJson(HttpServletResponse response, int code, String msg) throws IOException {
        response.setStatus(code);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            final ResponseEntity<String> body = ResponseEntity.status(code).body(msg);
            writer.print(objectMapper.writer().writeValueAsString(body));
        }
    }
}
