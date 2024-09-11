package com.github.ngodat0103.yamp.authsvc.cache;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.protocol.ProtocolVersion;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;

@Configuration
public class CacheConfiguration implements LettuceClientConfigurationBuilderCustomizer {
    @Bean
    RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory ){
        return RedisCacheManager.builder(redisConnectionFactory).build();
    }


    @Override
    public void customize(LettuceClientConfiguration.LettuceClientConfigurationBuilder clientConfigurationBuilder) {
        clientConfigurationBuilder.clientOptions(ClientOptions.builder()
                .protocolVersion(ProtocolVersion.RESP3)
                .build());
    }
}
