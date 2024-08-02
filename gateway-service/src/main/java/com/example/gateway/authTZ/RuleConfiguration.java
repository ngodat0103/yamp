package com.example.gateway.authTZ;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.DelegatingJwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
@ConfigurationProperties(prefix = "network-mask")
@EnableWebFluxSecurity()
public class RuleConfiguration {

    @Bean
    SecurityWebFilterChain externalFilter(ServerHttpSecurity http){
        http.cors(ServerHttpSecurity.CorsSpec::disable);
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);

        http.oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                        .jwtDecoder(jwtDecoder())
                        .jwtAuthenticationConverter(CustomJwtExtract())
                )
        );

        http.authorizeExchange(exchange ->exchange
                .pathMatchers(HttpMethod.GET,"/api/v1/auth/role/getAccountRoles").hasRole("ADMIN")
                .pathMatchers(HttpMethod.GET,"/api/v1/auth/api-docs").permitAll()
                .pathMatchers("/api/v1/auth/oauth2/token","/api/v1/auth/login","api/v1/auth/oauth2/authorize").permitAll()


                .pathMatchers("/api/v1/user/register","/api/v1/user/api-docs").permitAll()
                .pathMatchers("/api/v1/user/getMe").hasRole("CUSTOMER")


                .pathMatchers(HttpMethod.POST, "/api/v1/auth/account/**").hasAuthority("SCOPE_auth-service.write")
                .pathMatchers(HttpMethod.GET, "/api/v1/auth/account").hasAuthority("SCOPE_auth-service.read")

                .pathMatchers("/ui-docs/**").permitAll()
                .pathMatchers("/api-docs/**").permitAll()
                .pathMatchers("webjars/**").permitAll()
                .anyExchange().authenticated()
        );
        return http.build();
    }



    @Bean
    ReactiveJwtDecoder jwtDecoder(){
        return NimbusReactiveJwtDecoder
                .withJwkSetUri("http://auth-service:8001/api/v1/auth/oauth2/jwks")
                .build();
    }

    Converter<Jwt, Mono<AbstractAuthenticationToken>> CustomJwtExtract(){
        final Collection<Converter<Jwt, Collection<GrantedAuthority>>> listConverter = getConverters();

        DelegatingJwtGrantedAuthoritiesConverter delegatingJwtGrantedAuthoritiesConverter = new DelegatingJwtGrantedAuthoritiesConverter(listConverter);

       JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
       jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(delegatingJwtGrantedAuthoritiesConverter);

       
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);

    }

    private static Collection<Converter<Jwt, Collection<GrantedAuthority>>> getConverters() {
        Collection<Converter<Jwt,Collection<GrantedAuthority>>> listConverter = new ArrayList<>();


        JwtGrantedAuthoritiesConverter jwtExtractScopeConverter = new JwtGrantedAuthoritiesConverter();
        JwtGrantedAuthoritiesConverter jwtExtractRoleConverter = new JwtGrantedAuthoritiesConverter();
        jwtExtractRoleConverter.setAuthoritiesClaimName("roles");
        jwtExtractRoleConverter.setAuthorityPrefix("ROLE_");


        listConverter.add(jwtExtractScopeConverter); // for extract scope
        listConverter.add(jwtExtractRoleConverter); // for extract role
        return listConverter;
    }
}
