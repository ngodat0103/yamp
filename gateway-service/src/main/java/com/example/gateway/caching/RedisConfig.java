package com.example.gateway.caching;


import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cloud.gateway.filter.factory.cache.*;
import org.springframework.cloud.gateway.filter.factory.cache.keygenerator.CacheKeyGenerator;
import org.springframework.cloud.gateway.filter.factory.cache.keygenerator.UriKeyValueGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;

@Configuration
public class RedisConfig {
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        return RedisCacheManager.create(connectionFactory);
    }

    @Bean ResponseCacheManager responseCacheManager(CacheManager cacheManager) {
        CacheKeyGenerator cacheKeyGenerator = new CacheKeyGenerator();
        Cache cache = cacheManager.getCache("global-cache");
        LocalResponseCacheProperties.RequestOptions requestOptions = new LocalResponseCacheProperties.RequestOptions();
        Duration duration = Duration.ofMinutes(15);
        return new ResponseCacheManager(cacheKeyGenerator,cache,duration,requestOptions);

    }
    @Bean
    ResponseCacheGatewayFilter responseCacheGatewayFilter(ResponseCacheManager  responseCacheManager ) {
        return new ResponseCacheGatewayFilter(responseCacheManager );
    }


}