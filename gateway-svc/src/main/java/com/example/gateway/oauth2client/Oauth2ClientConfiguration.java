package com.example.gateway.oauth2client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;

@Configuration
public class Oauth2ClientConfiguration {
    @Bean
    ReactiveRedisOperations<Object, Object> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory){
        RedisSerializer<Object> keySerializer = RedisSerializer.java();
        RedisSerializer<Object> valueSerializer = RedisSerializer.java();
        RedisSerializationContext.RedisSerializationContextBuilder<Object, Object> serializationRedisBuilder =
                RedisSerializationContext.newSerializationContext(keySerializer)
                        .value(valueSerializer);
        return new ReactiveRedisTemplate<>(factory, serializationRedisBuilder.build());
    }
    @Bean
    ReactiveOAuth2AuthorizedClientService oAuth2AuthorizedClientService(ReactiveRedisOperations<Object, Object> redisOperations){
        return new RedisReactiveOAuth2AuthorizedClientService(redisOperations);
    }

}
