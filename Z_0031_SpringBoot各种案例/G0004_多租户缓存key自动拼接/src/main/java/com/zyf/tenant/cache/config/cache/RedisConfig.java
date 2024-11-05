package com.zyf.tenant.cache.config.cache;

import com.zyf.tenant.cache.config.tenant.CustomKeyPrefix;
import com.zyf.tenant.cache.config.tenant.TenantConfigProperties;
import com.zyf.tenant.cache.tenant.TenantContextHolder;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizers;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * redis配置
 */
@EnableCaching
@Configuration
@AutoConfigureBefore(RedisAutoConfiguration.class)
@EnableConfigurationProperties(CacheProperties.class)
public class RedisConfig {

    private final CacheProperties cacheProperties;
    private final TenantConfigProperties tenantConfigProperties;

    private final CacheManagerCustomizers customizerInvoker;

    @Nullable
    private final RedisCacheConfiguration redisCacheConfiguration;

    RedisConfig(CacheProperties cacheProperties,
                TenantConfigProperties tenantConfigProperties,
                CacheManagerCustomizers customizerInvoker,
                ObjectProvider<RedisCacheConfiguration> redisCacheConfiguration) {
        this.cacheProperties = cacheProperties;
        this.tenantConfigProperties = tenantConfigProperties;
        this.customizerInvoker = customizerInvoker;
        this.redisCacheConfiguration = redisCacheConfiguration.getIfAvailable();

        TenantContextHolder.setWhiteKeyPrefixs(tenantConfigProperties.getWhiteKeyPrefixs());
        TenantContextHolder.setWhiteUrls(tenantConfigProperties.getWhiteUrls());
    }

    @Primary
    @Bean
    @SuppressWarnings(value = {"unchecked", "rawtypes"})
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);

        // 统一给 RedisTemplate 加租户前缀
        if (tenantConfigProperties.isEnable()) {
            template.setKeySerializer(new TenantStringRedisSerializer());
        } else {
            template.setKeySerializer(new StringRedisSerializer());
        }
        template.setValueSerializer(serializer);

        // Hash的key也采用StringRedisSerializer的序列化方式
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        template.afterPropertiesSet();
        return template;
    }


    /**
     * 重写 redis缓存管理器，为 @Cache 缓存注解自动拼接租户
     */
    @Primary
    @Bean("cacheResolver")
    public CacheManager redisCacheManager(ObjectProvider<RedisConnectionFactory> connectionFactoryObjectProvider) {
        RedisConnectionFactory connectionFactory = connectionFactoryObjectProvider.getIfAvailable();
        Objects.requireNonNull(connectionFactory, "Bean RedisConnectionFactory is null.");
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
        RedisCacheConfiguration cacheConfiguration = this.determineConfiguration();
        List<String> cacheNames = this.cacheProperties.getCacheNames();
        Map<String, RedisCacheConfiguration> initialCaches = new LinkedHashMap<>();
        if (!cacheNames.isEmpty()) {
            Map<String, RedisCacheConfiguration> cacheConfigMap = new LinkedHashMap<>(cacheNames.size());
            cacheNames.forEach(it -> cacheConfigMap.put(it, cacheConfiguration));
            initialCaches.putAll(cacheConfigMap);
        }
        boolean allowInFlightCacheCreation = true;
        boolean enableTransactions = false;
        RedisAutoCacheManager cacheManager = new RedisAutoCacheManager(
                tenantConfigProperties,
                redisCacheWriter, cacheConfiguration, allowInFlightCacheCreation, initialCaches
        );
        cacheManager.setTransactionAware(enableTransactions);
        return this.customizerInvoker.customize(cacheManager);
    }

    private RedisCacheConfiguration determineConfiguration() {
        if (this.redisCacheConfiguration != null) {
            return this.redisCacheConfiguration;
        } else {
             // 获取Redis配置信息
            CacheProperties.Redis redisProperties = this.cacheProperties.getRedis();
            // 获取Redis默认配置
            RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();

            // 指定序列化器为GenericJackson2JsonRedisSerializer
            config = config.serializeValuesWith(RedisSerializationContext.SerializationPair
                    .fromSerializer(new GenericJackson2JsonRedisSerializer()));

            // 过期时间设置
            if (redisProperties.getTimeToLive() != null) {
                config = config.entryTtl(redisProperties.getTimeToLive());
            }

            // 替换前缀生成器（有前缀和无前缀）
            config = config.computePrefixWith(CustomKeyPrefix.simple());
            if (redisProperties.getKeyPrefix() != null) {
                config = config.computePrefixWith(CustomKeyPrefix.prefixed(redisProperties.getKeyPrefix()));
            }
            // 缓存空值配置
            if (!redisProperties.isCacheNullValues()) {
                config = config.disableCachingNullValues();
            }
            // 是否启用前缀
            if (!redisProperties.isUseKeyPrefix()) {
                config = config.disableKeyPrefix();
            }

            return config;
        }
    }

}
