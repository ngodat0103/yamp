package com.github.ngodat0103.yamp.authsvc.authserver;

import com.github.ngodat0103.yamp.authsvc.persistence.repository.AccountRepository;
import com.github.ngodat0103.yamp.authsvc.service.impl.UserDetailServiceImpl;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

@Configuration
@Setter
@Getter
@ConfigurationProperties(prefix = "init-swagger-client")
public class AuthConfiguration {
    private String swaggerSecret;
    private String redirectUri;
    private String logoutUri;

    @Bean
    RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate){

        return new JdbcRegisteredClientRepository(jdbcTemplate);
    }

    @Bean
    OAuth2AuthorizationService authorizationService(JdbcOperations jdbcOperations, RegisteredClientRepository registeredClientRepository){
        return new JdbcOAuth2AuthorizationService(jdbcOperations, registeredClientRepository);
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
