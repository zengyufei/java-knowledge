package com.zyf.tenant.cache.tenant;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.experimental.UtilityClass;
import org.springframework.util.AntPathMatcher;

import java.util.concurrent.Callable;

/**
 * 租户工具类
 */
@UtilityClass
public class TenantContextHolder {

    private String[] whiteKeyPrefixs;
    private String[] whiteUrls;

    private final ThreadLocal<String> THREAD_LOCAL_TENANT = new TransmittableThreadLocal<>();

    private final ThreadLocal<Boolean> THREAD_LOCAL_TENANT_SKIP_FLAG = new TransmittableThreadLocal<>();

    /**
     * TTL 设置租户ID<br/>
     * <b>谨慎使用此方法,避免嵌套调用。尽量使用 {@code TenantBroker} </b>
     *
     * @param tenantId
     * @see TenantBroker
     */
    public void setTenantId(String tenantId) {
        THREAD_LOCAL_TENANT.set(tenantId);
    }

    /**
     * 设置是否过滤的标识
     */
    public void setTenantSkip() {
        THREAD_LOCAL_TENANT_SKIP_FLAG.set(Boolean.TRUE);
    }

    /**
     * 设置是否过滤的标识
     */
    public void skipTenant(Runnable runnable) {
        THREAD_LOCAL_TENANT_SKIP_FLAG.set(Boolean.TRUE);
        try {
            runnable.run();
        } finally {
            THREAD_LOCAL_TENANT_SKIP_FLAG.remove();
        }
    }

    /**
     * 设置是否过滤的标识
     */
    public <T> T skipTenant(Callable<T> callable) {
        THREAD_LOCAL_TENANT_SKIP_FLAG.set(Boolean.TRUE);
        try {
            return callable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            THREAD_LOCAL_TENANT_SKIP_FLAG.remove();
        }
    }

    /**
     * 移除是否过滤的标识
     */
    public void removeTenantSkip() {
        THREAD_LOCAL_TENANT_SKIP_FLAG.remove();
    }

    /**
     * 获取TTL中的租户ID
     *
     * @return
     */
    public String getTenantId() {
        return THREAD_LOCAL_TENANT.get();
    }

    /**
     * 获取是否跳过租户过滤的标识
     *
     * @return
     */
    public Boolean getTenantSkip() {
        return THREAD_LOCAL_TENANT_SKIP_FLAG.get() != null && THREAD_LOCAL_TENANT_SKIP_FLAG.get();
    }

    public void clear() {
        THREAD_LOCAL_TENANT.remove();
        THREAD_LOCAL_TENANT_SKIP_FLAG.remove();
    }

    public static String[] getWhiteKeyPrefixs() {
        return whiteKeyPrefixs;
    }

    public static void setWhiteKeyPrefixs(String[] whiteKeyPrefixs) {
        TenantContextHolder.whiteKeyPrefixs = whiteKeyPrefixs;
    }

    public static String[] getWhiteUrls() {
        return whiteUrls;
    }

    public static void setWhiteUrls(String[] whiteUrls) {
        TenantContextHolder.whiteUrls = whiteUrls;
    }


    /**
     * 查找指定字符串是否匹配指定字符串列表中的任意一个字符串
     *
     * @param str 指定字符串
     * @return 是否匹配
     */
    public static boolean matches(String str) {

        if (StrUtil.isBlank(str) || ArrayUtil.isEmpty(TenantContextHolder.getWhiteUrls())) {
            return false;
        }
        for (String pattern : TenantContextHolder.getWhiteUrls()) {
            if (isMatch(pattern, str)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isMatch(String pattern, String url) {
        AntPathMatcher matcher = new AntPathMatcher();
        return matcher.match(pattern, url);
    }
}
