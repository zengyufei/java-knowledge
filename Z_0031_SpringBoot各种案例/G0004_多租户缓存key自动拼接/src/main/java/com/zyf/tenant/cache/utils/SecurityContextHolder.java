package com.zyf.tenant.cache.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.zyf.tenant.cache.constant.SecurityConstants;
import com.zyf.tenant.cache.entity.LoginUser;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 获取当前线程变量中的 用户id、用户名称、Token等信息
 * 注意： 必须在网关通过请求头的方法传入，同时在HeaderInterceptor拦截器设置值。 否则这里无法获取
 */
public class SecurityContextHolder {
    private static final TransmittableThreadLocal<Map<String, Object>> THREAD_LOCAL = new TransmittableThreadLocal<>();

    public static void set(String key, Object value) {
        Map<String, Object> map = getLocalMap();
        map.put(key, value == null ? StrUtil.EMPTY : value);
    }

    public static String get(String key) {
        Map<String, Object> map = getLocalMap();
        return Convert.toStr(map.getOrDefault(key, StrUtil.EMPTY));
    }

    public static <T> T get(String key, Class<T> clazz) {
        Map<String, Object> map = getLocalMap();
        final Object orDefault = map.getOrDefault(key, null);
        if (orDefault != null) {
            return (T) orDefault;
        }
        return null;
    }

    public static Map<String, Object> getLocalMap() {
        Map<String, Object> map = THREAD_LOCAL.get();
        if (map == null) {
            map = new ConcurrentHashMap<String, Object>();
            THREAD_LOCAL.set(map);
        }
        return map;
    }

    public static void setLocalMap(Map<String, Object> threadLocalMap) {
        THREAD_LOCAL.set(threadLocalMap);
    }

    public static String getUserId() {
        return get(SecurityConstants.DETAILS_USER_ID);
    }

    public static void setUserId(String account) {
        set(SecurityConstants.DETAILS_USER_ID, account);
    }

    public static String getUserName() {
        return get(SecurityConstants.DETAILS_USERNAME);
    }

    public static void setUserName(String username) {
        set(SecurityConstants.DETAILS_USERNAME, username);
    }

    public static String getPermission() {
        return get(SecurityConstants.ROLE_PERMISSION);
    }

    public static void setPermission(String permissions) {
        set(SecurityConstants.ROLE_PERMISSION, permissions);
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }

    public static String getToken() {
        return IdUtil.fastSimpleUUID();
    }

    public static LoginUser getLoginUser(String token) {
        final LoginUser loginUser = new LoginUser();
        final ArrayList<JSONObject> tenants = new ArrayList<>();
        {
            final JSONObject e = new JSONObject();
            e.set("tenantId", "1");
            tenants.add(e);
        }
        {
            final JSONObject e = new JSONObject();
            e.set("tenantId", "2");
            tenants.add(e);
        }
        loginUser.setTenants(tenants);
        return loginUser;
    }
}
