package com.zyf.tenant.cache.config.tenant;

import com.zyf.tenant.cache.interceptor.RestTemplateRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;

/**
 * 租户信息拦截
 */
@Configuration
public class TenantConfiguration {

//	@Bean
//	public RequestInterceptor feignTenantInterceptor() {
//		return new FeignTenantInterceptor();
//	}

	@Bean
	public ClientHttpRequestInterceptor myRestTemplateRequestInterceptor() {
		return new RestTemplateRequestInterceptor();
	}

}
