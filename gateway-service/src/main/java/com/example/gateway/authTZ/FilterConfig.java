package com.example.gateway.authTZ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.commons.util.InetUtilsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.net.Inet4Address;

@Configuration
@ConfigurationProperties(prefix = "network-mask")
@EnableWebFluxSecurity
public class FilterConfig {
    private String prefix;
    private String instanceIp;

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    public void setInstanceIp(String instanceIp) {
        this.instanceIp = instanceIp;
    }

    @Bean
    SecurityWebFilterChain externalFilter(ServerHttpSecurity http, InetUtils inetUtils){
        http.cors(ServerHttpSecurity.CorsSpec::disable);
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);

        http.oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                        .jwtDecoder(jwtDecoder())
                        .jwtAuthenticationConverter(roleExtract())
                )
        );

        String networkMask = instanceIp+"/"+ prefix;
        System.out.println("Network mask: "+networkMask);
        http.authorizeExchange(exchange ->exchange
                .pathMatchers(HttpMethod.GET,"/api/v1/auth/role/getAccountRoles").hasRole("ADMIN")
                .pathMatchers(HttpMethod.GET,"/api/v1/auth/api-docs").permitAll()
                .pathMatchers("/api/v1/auth/oauth2/token","/api/v1/auth/login","api/v1/auth/oauth2/authorize").permitAll()


                .pathMatchers("/api/v1/user/register","/api/v1/user/api-docs").permitAll()
                .pathMatchers("/api/v1/user/getMe").hasRole("CUSTOMER")


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
