package org.example.authservice.config;
import lombok.Getter;
import lombok.Setter;
import org.example.authservice.repository.AccountRepository;
import org.example.authservice.service.impl.UserDetailServiceImpl;
import org.example.authservice.service.impl.RedisOauth2AuthorizationService;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.autoconfigure.security.oauth2.server.servlet.OAuth2AuthorizationServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Configuration
@Setter
@Getter
@ConfigurationProperties(prefix = "init-swagger-client")
public class AuthConfiguration {
    private String swaggerSecret;
    private String redirectUri;
    private String logoutUri;

    @Bean
    RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate,
                                                          PasswordEncoder passwordEncoder
    ){

        JdbcRegisteredClientRepository jdbcRegisteredClientRepository = new JdbcRegisteredClientRepository(jdbcTemplate);
        RegisteredClient swaggerClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("swagger-client")
                .clientSecret(passwordEncoder.encode(swaggerSecret))
                .clientSecretExpiresAt(null)
                .tokenSettings(TokenSettings.builder().
                        accessTokenTimeToLive(Duration.ofMinutes(5)).
                        refreshTokenTimeToLive(Duration.ofDays(7)).
                        build()
                ).
                clientName("Swagger client")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantTypes(a -> {
                            a.add(AuthorizationGrantType.AUTHORIZATION_CODE);
                            a.add(AuthorizationGrantType.REFRESH_TOKEN);
                        })
                .redirectUri(redirectUri)
                .postLogoutRedirectUri(logoutUri)
                .build();

        jdbcRegisteredClientRepository.save(swaggerClient);
        return jdbcRegisteredClientRepository;
    }


    @Bean
    RedisOauth2AuthorizationService authorizationService(RedisOperations<String,String> redisOperations,
                                                         RegisteredClientRepository registeredClientRepository,
                                                         AutowireCapableBeanFactory autowireCapableBeanFactory){
        return new RedisOauth2AuthorizationService(redisOperations,registeredClientRepository,autowireCapableBeanFactory);
    }
    @Bean
    UserDetailServiceImpl userDetailService(AccountRepository accountRepository){
        return new UserDetailServiceImpl(accountRepository);
    }


    @Bean
    CachingUserDetailsService cachingUserDetailsService(UserDetailServiceImpl userDetailService, UserCache userCache){
        CachingUserDetailsService cachingUserDetailsService = new CachingUserDetailsService(userDetailService);
        cachingUserDetailsService.setUserCache(userCache);
        return cachingUserDetailsService;
    }
    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(CachingUserDetailsService cachingUserDetailsService, PasswordEncoder passwordEncoder){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(cachingUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }


}
