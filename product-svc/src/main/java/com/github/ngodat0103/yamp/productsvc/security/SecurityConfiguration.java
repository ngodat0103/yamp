package com.github.ngodat0103.yamp.productsvc.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    @Bean
//    @Profile({"pre-prod","prod"})
    SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(requests -> requests
                .requestMatchers(HttpMethod.GET,"/ui-docs/**","/api-docs/**","/swagger-ui/**").permitAll()
                .requestMatchers(HttpMethod.GET,"/categories/**").permitAll()
                .requestMatchers(HttpMethod.GET,"/products/**").permitAll()
                .anyRequest().authenticated()
        );
        http.oauth2ResourceServer(resource-> resource.jwt(Customizer.withDefaults()));
        return http.build();
    }

//    @Bean
//    @Profile({"local-dev","integration-test"})
//    // I use this setting for developing and testing only, does not use this in production
//    SecurityFilterChain apiFilterChain_dev(HttpSecurity http) throws Exception {
//        http.csrf(AbstractHttpConfigurer::disable);
//        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//        http.authorizeHttpRequests(requests -> requests.anyRequest().permitAll());
//
//        http.anonymous(anonymous -> {
//            String principal = "3fa85f64-5717-4562-b3fc-2c963f66afa6";
//            anonymous.principal(principal);
//            anonymous.authorities("ROLE_ADMIN");
//        });
//        return http.build();
//    }



}
