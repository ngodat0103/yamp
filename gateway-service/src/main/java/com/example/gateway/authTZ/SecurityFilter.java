package com.example.gateway.authTZ;

import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
public class SecurityFilter {
    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
        http.cors(ServerHttpSecurity.CorsSpec::disable);
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);


        http.oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                        .jwtDecoder(jwtDecoder())
                        .jwtAuthenticationConverter(roleExtract())
                )
        );


        http.authorizeExchange(exchange ->exchange
                .pathMatchers(HttpMethod.POST,"auth/account/register").permitAll()
                .pathMatchers(HttpMethod.GET,"auth/role/getAccountRoles").hasRole("ADMIN")
                .pathMatchers("auth/oauth2/**","auth/login").permitAll()
        );
        return http.build();
    }

    ReactiveJwtDecoder jwtDecoder(){
        return NimbusReactiveJwtDecoder
                .withJwkSetUri("http://auth-service:8001/auth/oauth2/jwks")
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
