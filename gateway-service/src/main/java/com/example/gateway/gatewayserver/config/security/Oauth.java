package com.example.gateway.gatewayserver.config.security;

import org.springframework.cache.Cache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Objects;

@Configuration
public class Oauth {

    @Bean
    JwtDecoder jwtDecoder(RedisCacheManager redisCacheManager) {
        Cache jwtCache = redisCacheManager.getCache("jwtCache");
        assert jwtCache != null;
        return NimbusJwtDecoder.withJwkSetUri("http://auth-service:8001/auth/oauth2/jwks").
                    cache(jwtCache).
                    build();
    }
    @Bean
    public JwtAuthenticationConverter jwtGrantedAuthoritiesConverter(){
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimDelimiter(" ");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter ;
    }
}
