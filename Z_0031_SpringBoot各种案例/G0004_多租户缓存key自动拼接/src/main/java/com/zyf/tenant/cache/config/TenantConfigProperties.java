package com.zyf.tenant.cache.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 多租户配置
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "tenant", ignoreUnknownFields = false)
public class TenantConfigProperties {

	/**
	 * 是否开启租户功能
	 */
	private boolean enable = false;

	/**
	 * 维护租户列名称
	 */
	private String column = "TENANT_ID";

	/**
	 * 多租户的数据表集合
	 */
	private List<String> tables = new ArrayList<>();

	/**
	 * 白名单 key 前缀
	 */
	private String[] whiteKeyPrefixs;

	/**
	 * 白名单 url
	 */
	private String[] whiteUrls;

}
