package com.example.userservice.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable());
        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                        .requestMatchers("/api/v1/user/login").authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/v1/role").hasAnyRole("REGULAR","ADMIN")
                        .requestMatchers(HttpMethod.POST,"api/v1/role").hasRole("ADMIN")
                        .anyRequest().permitAll()
        );

        http.cors(cors -> cors.configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setMaxAge(3600L);
                configuration.setAllowedMethods(Collections.singletonList("*"));
                configuration.setAllowedOrigins(Collections.singletonList("localhost:4000"));
                return configuration;
            }
        }));
        http.httpBasic(Customizer.withDefaults());
        http.formLogin(Customizer.withDefaults());
        return http.build();
    }

}
