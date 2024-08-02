package org.example.authservice.config;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.example.authservice.service.impl.RedisOauth2AuthorizationService;
import org.example.authservice.vault.HcpVault;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import java.text.ParseException;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class AuthServerConfiguration {
    @Bean
    RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate){
        return new JdbcRegisteredClientRepository(jdbcTemplate);
    }


    @Bean
    RedisOauth2AuthorizationService authorizationService(RedisOperations<String,String> redisOperations,
                                                         RegisteredClientRepository registeredClientRepository,
                                                         AutowireCapableBeanFactory autowireCapableBeanFactory){
        return new RedisOauth2AuthorizationService(redisOperations,registeredClientRepository,autowireCapableBeanFactory);
    }

}
