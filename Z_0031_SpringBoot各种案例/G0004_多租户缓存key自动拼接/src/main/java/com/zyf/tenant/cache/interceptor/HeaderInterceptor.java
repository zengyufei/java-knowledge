package com.zyf.tenant.cache.interceptor;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zyf.tenant.cache.config.TenantConfigProperties;
import com.zyf.tenant.cache.constant.FeignConstants;
import com.zyf.tenant.cache.constant.SecurityConstants;
import com.zyf.tenant.cache.entity.LoginUser;
import com.zyf.tenant.cache.tenant.TenantContextHolder;
import com.zyf.tenant.cache.utils.SecurityContextHolder;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

/**
 * HandlerInterceptor是最常规的，其拦截的http请求是来自于客户端浏览器之类的，是最常见的http请求拦截器；
 * 自定义请求头拦截器，将Header数据封装到线程变量中方便获取
 * 注意：此拦截器会同时验证当前用户有效期自动刷新有效期
 */
@Component
public class HeaderInterceptor implements AsyncHandlerInterceptor {

    @Autowired
    private TenantConfigProperties tenantConfigProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        String isIgnoreWhite = request.getHeader(FeignConstants.IS_IGNORE_WHITE);
        String fromSource = request.getHeader(FeignConstants.FROM_SOURCE);

        String url = request.getRequestURI();
        // 跳过不需要验证的路径
        if (TenantContextHolder.matches(url)) {
            isIgnoreWhite = "1";
        }

        String token = SecurityContextHolder.getToken();
        if (StringUtils.isNotEmpty(token)) {
            LoginUser loginUser = SecurityContextHolder.getLoginUser(token);
            if (loginUser != null) {
                SecurityContextHolder.set(SecurityConstants.LOGIN_USER, loginUser);
                SecurityContextHolder.setUserName(loginUser.getUserName());
                SecurityContextHolder.setUserId(loginUser.getUserId());
            }
            final List<JSONObject> tenants = loginUser.getTenants();
            if (!StrUtil.equalsIgnoreCase(fromSource, FeignConstants.INNER)
                    && !StrUtil.equals(isIgnoreWhite, "1")
                    && tenantConfigProperties.isEnable()
                    && CollUtil.isNotEmpty(tenants)) {
                final String tenantId = TenantContextHolder.getTenantId();
                final List<String> tenantIds = tenants.stream().map(e -> e.getStr("tenantId")).toList();
                if (!tenantIds.contains(tenantId)) {
                    returnJson(response, 408, "租户标识与用户不匹配");
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
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
