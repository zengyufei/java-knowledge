package com.zyf.tenant.cache.constant;

public class RedisConstants {

    //*************************** 租户 ******************************
    /**
     * 租户字段名
     */
    public static final String TENANT_ID = "TENANT_ID";
    /**
     * 默认租户id
     */
    public static final long TENANT_ID_1 = 1L;

    /**
     * 全局缓存，在缓存名称上加上该前缀表示该缓存不区分租户，比如:
     */
    public static final String GLOBALLY = "gl";
}
