package com.zyf.tenant.cache.config;

import cn.hutool.core.util.StrUtil;
import com.zyf.tenant.cache.constant.RedisConstants;
import com.zyf.tenant.cache.tenant.TenantContextHolder;
import com.zyf.tenant.cache.utils.RedisKeyResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 自定义序列化，用于保存租户号，隔离数据
 */
@Slf4j
public class TenantStringRedisSerializer implements RedisSerializer<String> {

    private final Charset charset;
    public static final StringRedisSerializer US_ASCII;
    public static final StringRedisSerializer ISO_8859_1;
    public static final StringRedisSerializer UTF_8;


    private static final Boolean IS_LOG = true;

    public final static String COLON = ":";


    public TenantStringRedisSerializer() {
        this(StandardCharsets.UTF_8);
    }

    public TenantStringRedisSerializer(Charset charset) {
        Assert.notNull(charset, "Charset must not be null!");
        this.charset = charset;
    }

    public String deserialize(@Nullable byte[] bytes) {
        return (bytes == null ? null : new String(bytes, charset).replaceFirst(TenantContextHolder.getTenantId() + ":", ""));
    }

    public byte[] serialize(@Nullable String string) {

        if (StrUtil.isBlank(string)) {
            echoLog(string);
            return null;
        }

        if (StrUtil.startWithAnyIgnoreCase(string, RedisConstants.GLOBALLY)
                || StrUtil.startWithAnyIgnoreCase(string, TenantContextHolder.getWhiteKeyPrefixs())) {
            echoLog(string);
            return string.getBytes(charset);
        }

        String tenantId = TenantContextHolder.getTenantId();
        if (StrUtil.isBlank(tenantId)) {
            echoLog(string);
            return string.getBytes(charset);
        }

        // 本身带有多租户ID的不拼接
        if (string.indexOf(COLON) > 0 && string.startsWith(tenantId + COLON)) {
            echoLog(string);
            return string.getBytes(charset);
        }

        // 拼接多租户ID
        echoLog(tenantId + COLON + string);
        return (tenantId + COLON + string).getBytes(charset);
    }

    private void echoLog(String key) {
        if (IS_LOG) {
            log.info("redis的key:" + key);
        }
    }

    public Class<?> getTargetType() {
        return String.class;
    }

    static {
        US_ASCII = new StringRedisSerializer(StandardCharsets.US_ASCII);
        ISO_8859_1 = new StringRedisSerializer(StandardCharsets.ISO_8859_1);
        UTF_8 = new StringRedisSerializer(StandardCharsets.UTF_8);
    }
}
