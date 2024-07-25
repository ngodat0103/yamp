package com.example.gateway.gatewayserver.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity(debug = true)
public class DefaultFilter {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(
                authorizeHttpRequest->
                        authorizeHttpRequest.requestMatchers("/auth/login","/auth/oauth2/authorize").permitAll()
                                .requestMatchers(HttpMethod.GET,"/auth/role/getAccountRoles").hasAnyRole("USER","ADMIN")
                                .anyRequest().permitAll()

        );

        http.oauth2ResourceServer(oauth2-> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }

}
