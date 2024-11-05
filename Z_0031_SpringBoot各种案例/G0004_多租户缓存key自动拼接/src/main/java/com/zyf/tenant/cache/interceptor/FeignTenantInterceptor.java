//package com.zyf.tenant.cache.interceptor;
//
//import com.zyf.tenant.cache.constant.RedisConstants;
//import com.zyf.tenant.cache.tenant.TenantContextHolder;
//import feign.RequestInterceptor;
//import feign.RequestTemplate;
//import lombok.extern.slf4j.Slf4j;
//
///**
// * RequestInterceptor常被称为是Feign拦截器，由于Feign调用底层实际上还是http调用，因此也是一个http拦截器，在项目中使用Feign调用的时候，可以使用此拦截器；
// */
//@Slf4j
//public class FeignTenantInterceptor implements RequestInterceptor {
//
//	@Override
//	public void apply(RequestTemplate requestTemplate) {
//		if (TenantContextHolder.getTenantId() == null) {
//			log.debug("TTL 中的 租户ID为空，feign拦截器 >> 跳过");
//			return;
//		}
//		requestTemplate.header(RedisConstants.TENANT_ID, TenantContextHolder.getTenantId().toString());
//	}
//
//}
