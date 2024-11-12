package com.zyf.tenant.cache.config.cache;

import cn.hutool.core.text.StrPool;
import com.zyf.tenant.cache.config.tenant.TenantConfigProperties;
import com.zyf.tenant.cache.tenant.TenantContextHolder;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizers;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
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
import java.util.Objects;

/**
 * redis配置
 */
@Configuration
@EnableCaching
@AutoConfigureBefore(RedisAutoConfiguration.class)
public class RedisConfig {


    private final ObjectProvider<List<CacheManagerCustomizer<?>>> customizers;

    RedisConfig(TenantConfigProperties tenantConfigProperties,
                ObjectProvider<List<CacheManagerCustomizer<?>>> customizers) {
        this.customizers = customizers;

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
//        FastJson2JsonRedisSerializer serializer = new FastJson2JsonRedisSerializer(Object.class);

        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new TenantStringRedisSerializer());
//            template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);

        // Hash的key也采用StringRedisSerializer的序列化方式
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        template.afterPropertiesSet();
        return template;
    }


    @Primary
    @Bean("cacheResolver")
    public CacheManager redisCacheManager(ObjectProvider<RedisConnectionFactory> connectionFactoryObjectProvider) {
        RedisConnectionFactory connectionFactory = connectionFactoryObjectProvider.getIfAvailable();
        Objects.requireNonNull(connectionFactory, "Bean RedisConnectionFactory is null.");
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
        // 获取Redis默认配置
        RedisCacheConfiguration cacheConfiguration = determineConfiguration();
        RedisAutoCacheManager cacheManager = new RedisAutoCacheManager(
                redisCacheWriter, cacheConfiguration, true, new LinkedHashMap<>()
        );
        cacheManager.setTransactionAware(false);
        return new CacheManagerCustomizers(customizers.getIfAvailable()).customize(cacheManager);
    }

    private RedisCacheConfiguration determineConfiguration() {
        // 获取Redis默认配置
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
         // 指定序列化器为GenericJackson2JsonRedisSerializer
        config = config.serializeValuesWith(RedisSerializationContext.SerializationPair
                .fromSerializer(new Jackson2JsonRedisSerializer(Object.class)));

        // 替换前缀生成器（有前缀和无前缀）
        config = config.computePrefixWith(name -> name + StrPool.COLON);
        return config;
    }

}
