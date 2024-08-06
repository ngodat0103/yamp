package org.example.authservice.authserver;
import lombok.Getter;
import lombok.Setter;
import org.example.authservice.repository.AccountRepository;
import org.example.authservice.service.impl.UserDetailServiceImpl;
import org.example.authservice.service.impl.RedisOauth2AuthorizationService;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
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
                .clientId("gateway-service")
                .clientSecret(passwordEncoder.encode("gateway-service-secret"))
                .clientSecretExpiresAt(null)
                .tokenSettings(TokenSettings.builder().
                        accessTokenTimeToLive(Duration.ofMinutes(15)).
                        build()
                ).
                clientName("Gateway service")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("SCOPE_auth-service.verify")
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
