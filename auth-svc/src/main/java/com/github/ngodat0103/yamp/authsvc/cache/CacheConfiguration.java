package com.github.ngodat0103.yamp.authsvc.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.core.userdetails.cache.SpringCacheBasedUserCache;

@Configuration
public class CacheConfiguration {
    @Bean
    RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory ){
        return RedisCacheManager.builder(redisConnectionFactory).build();
    }


    @Bean
    SpringCacheBasedUserCache springCacheBasedUserCache(CacheManager cacheManager){
        Cache cache = cacheManager.getCache("userCache");
        return new SpringCacheBasedUserCache(cache);
    }
}
