package com.example.userservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class DefaultFilter {


    @Bean
    @Order(0)
    SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        http.cors(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);
        http.oauth2Login(AbstractHttpConfigurer::disable);
        http.oauth2Client(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests((authorizeRequests) -> authorizeRequests.anyRequest().permitAll());
        return http.build();
    }


}
