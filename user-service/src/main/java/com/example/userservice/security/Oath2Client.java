package com.example.userservice.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class Oath2Client {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.oauth2Client(Customizer.withDefaults());
        http.authorizeHttpRequests((authorizeRequests) -> authorizeRequests.anyRequest().permitAll());
        return http.build();
    }
}
