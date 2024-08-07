package com.example.userservice.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.session.DisableEncodeUrlFilter;
import org.springframework.session.data.redis.RedisSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.SessionRepositoryFilter;

@Configuration
@EnableWebSecurity(debug = true)
@EnableRedisHttpSession
public class SecurityConfiguration {
    @Bean
    SecurityFilterChain securityFilterChain (HttpSecurity http, RedisSessionRepository redisSessionRepository
                                             ) throws Exception {

        // disable all security features for internal communication microservices
        http.cors(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);
        http.oauth2Login(AbstractHttpConfigurer::disable);
        http.oauth2Client(AbstractHttpConfigurer::disable);
        http.logout(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);




        // These settings for distributed session management
        http.sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.NEVER));
        HttpSessionSecurityContextRepository httpSessionSecurityContextRepository = new HttpSessionSecurityContextRepository();
        httpSessionSecurityContextRepository.setAllowSessionCreation(false);
        http.securityContext(securitycontext ->
                securitycontext.securityContextRepository(httpSessionSecurityContextRepository)
                );
        http.addFilterBefore(new SessionRepositoryFilter<>(redisSessionRepository), DisableEncodeUrlFilter.class);



        http.authorizeHttpRequests((authorizeRequests) -> authorizeRequests.anyRequest().permitAll());
        http.headers(header -> header.addHeaderWriter(new TraceHeaderWriter()));
        return http.build();
    }


}
