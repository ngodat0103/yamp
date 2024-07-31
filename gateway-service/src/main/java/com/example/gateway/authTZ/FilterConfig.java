package com.example.gateway.authTZ;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.net.Inet4Address;

@Configuration
public class FilterConfig {
    @Bean
    SecurityWebFilterChain externalFilter(ServerHttpSecurity http){
        http.cors(ServerHttpSecurity.CorsSpec::disable);
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);

        http.oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                        .jwtDecoder(jwtDecoder())
                        .jwtAuthenticationConverter(roleExtract())
                )
        );


        http.authorizeExchange(exchange ->exchange
                .pathMatchers(HttpMethod.POST,"/api/v1/auth/account/register").hasIpAddress("127.0.0.1/24")
                .pathMatchers(HttpMethod.POST,"/api/v1/auth/account/role").hasIpAddress("127.0.0.1/24")
                .pathMatchers(HttpMethod.GET,"/api/v1/auth/role/getAccountRoles").hasRole("ADMIN")
                .pathMatchers("/api/v1/auth/oauth2/**","/api/v1/auth/login").permitAll()
                .pathMatchers("/api/v1/user/register").permitAll()
                .pathMatchers("/api/v1/user/getMe").hasRole("CUSTOMER")
                .pathMatchers("/api/v1/user/**").permitAll()
                .anyExchange().permitAll()
        );
        return http.build();
    }



    @Bean
    ReactiveJwtDecoder jwtDecoder(){
        return NimbusReactiveJwtDecoder
                .withJwkSetUri("http://auth-service:8001/api/v1/auth/oauth2/jwks")
                .build();
    }

    Converter<Jwt, Mono<AbstractAuthenticationToken>> roleExtract(){
       JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
       JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
       jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
       jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
       jwtGrantedAuthoritiesConverter.setAuthoritiesClaimDelimiter(" ");
       jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
       return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);

    }
}
