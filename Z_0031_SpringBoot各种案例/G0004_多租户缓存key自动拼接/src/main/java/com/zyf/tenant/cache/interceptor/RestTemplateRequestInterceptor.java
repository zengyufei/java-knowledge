package com.zyf.tenant.cache.interceptor;

import com.zyf.tenant.cache.constant.RedisConstants;
import com.zyf.tenant.cache.tenant.TenantContextHolder;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * ClientHttpRequestInterceptor是对RestTemplate的请求进行拦截的，在项目中直接使用restTemplate.getForObject的时候，会对这种请求进行拦截，经常被称为：RestTempalte拦截器或者Ribbon拦截器；
 * 传递 RestTemplate 请求的租户ID
 */
public class RestTemplateRequestInterceptor implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {

		if (TenantContextHolder.getTenantId() != null) {
			request.getHeaders().set(RedisConstants.TENANT_ID, String.valueOf(TenantContextHolder.getTenantId()));
		}

		return execution.execute(request, body);
	}

}
