package com.zyf.tenant.cache.utils;

import cn.hutool.core.util.StrUtil;
import com.zyf.tenant.cache.constant.RedisConstants;

public class RedisKeyResolver {
    public static String globally(String... keys) {
        StringBuilder sb = new StringBuilder(RedisConstants.GLOBALLY);
        for (String key : keys) {
            sb.append(StrUtil.COLON).append(key);
        }
        return sb.toString();
    }
}
