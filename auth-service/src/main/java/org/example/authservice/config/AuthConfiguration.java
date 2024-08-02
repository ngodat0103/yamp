package org.example.authservice.config;
import org.example.authservice.repository.AccountRepository;
import org.example.authservice.service.impl.UserDetailServiceImpl;
import org.example.authservice.service.impl.RedisOauth2AuthorizationService;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

@Configuration
public class AuthConfiguration {
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
