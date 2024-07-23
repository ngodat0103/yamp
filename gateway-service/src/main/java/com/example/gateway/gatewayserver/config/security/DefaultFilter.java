package com.example.gateway.gatewayserver.config.security;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
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
                        authorizeHttpRequest.requestMatchers("/account/oauth2/**").permitAll()
                                .requestMatchers("/account/**").hasAnyRole("USER","ADMIN")
                                .requestMatchers("/account/role/**").hasAnyRole("USER","ADMIN")
                                .requestMatchers("/account/role/**").hasAnyRole("ADMIN")
                                .anyRequest().permitAll()

        );

        http.oauth2ResourceServer(oauth2-> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }

}
