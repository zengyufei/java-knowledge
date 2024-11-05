package com.zyf.tenant.cache.config.tenant;

import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.util.Assert;

public interface CustomKeyPrefix extends CacheKeyPrefix {

    String SEPARATOR = ":";

    String compute(String cacheName);

    static CustomKeyPrefix simple() {
        return (name) -> name + SEPARATOR;
    }

    static CustomKeyPrefix prefixed(String prefix) {
        Assert.notNull(prefix, "Prefix must not be null!");
        return (name) -> prefix + name + SEPARATOR;
    }

}
