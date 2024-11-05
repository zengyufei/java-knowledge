package com.zyf.tenant.cache.filter;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zyf.tenant.cache.config.tenant.TenantConfigProperties;
import com.zyf.tenant.cache.constant.FeignConstants;
import com.zyf.tenant.cache.constant.RedisConstants;
import com.zyf.tenant.cache.tenant.TenantContextHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TenantContextHolderFilter extends GenericFilterBean {

    private final static String UNDEFINED_STR = "undefined";

    @Autowired
    private TenantConfigProperties tenantConfigProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        if (!tenantConfigProperties.isEnable()) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String isIgnoreWhite = request.getHeader(FeignConstants.IS_IGNORE_WHITE);
        String fromSource = request.getHeader(FeignConstants.FROM_SOURCE);
        String headerTenantId = request.getHeader(RedisConstants.TENANT_ID);
        String paramTenantId = request.getParameter(RedisConstants.TENANT_ID);

        String url = request.getRequestURI();
        // 跳过不需要验证的路径
        if (TenantContextHolder.matches(url)) {
            isIgnoreWhite = "1";
        }
        log.debug("获取header中的租户ID为:{}", headerTenantId);

        if (StrUtil.isNotBlank(headerTenantId) && !StrUtil.equals(UNDEFINED_STR, headerTenantId)) {
            TenantContextHolder.setTenantId(headerTenantId);
        } else if (StrUtil.isNotBlank(paramTenantId) && !StrUtil.equals(UNDEFINED_STR, paramTenantId)) {
            TenantContextHolder.setTenantId(paramTenantId);
        } else {
            if (!StrUtil.equalsIgnoreCase(fromSource, FeignConstants.INNER) && !StrUtil.equals(isIgnoreWhite, "1")) {
                returnJson(response, 408, "租户标识与用户不匹配");
                return;
            }
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            TenantContextHolder.clear();
        }
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
