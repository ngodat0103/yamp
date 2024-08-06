package com.example.gateway.authTZ;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.DelegatingJwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationEntryPoint;
import org.springframework.security.web.server.savedrequest.WebSessionServerRequestCache;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
public class SecurityConfiguration {


    @Bean
    SecurityWebFilterChain internalFilter(ServerHttpSecurity http){
        http.cors(ServerHttpSecurity.CorsSpec::disable);
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
        http.oauth2ResourceServer(oauth2 -> {
                    oauth2.jwt(jwt -> jwt
                            .jwtDecoder(jwtDecoder())
                            .jwtAuthenticationConverter(CustomJwtExtract())
                    );

                }
        );


        http.authorizeExchange(exchange ->exchange
                .pathMatchers(HttpMethod.GET,"/api/v1/auth/api-docs").permitAll()
                .pathMatchers("/api/v1/auth/oauth2/token",
                        "/api/v1/auth/login",
                        "/api/v1/auth/oauth2/authorize"
                ).permitAll()

                .pathMatchers(HttpMethod.POST, "/api/v1/auth/account/**").hasAuthority("SCOPE_auth-service.write")
                .pathMatchers(HttpMethod.GET, "/api/v1/auth/account").hasAuthority("SCOPE_auth-service.read")





                .pathMatchers(HttpMethod.POST,"/api/v1/auth/logout").permitAll()
                .pathMatchers("/api/v1/user/register","/api/v1/user/api-docs").permitAll()
                .pathMatchers("/api/v1/user/getMe").hasRole("CUSTOMER")
                .pathMatchers("/ui-docs/**").permitAll()
                .pathMatchers("/api-docs/**").permitAll()
                .pathMatchers("webjars/**").permitAll()
                .pathMatchers(HttpMethod.GET,"/actuator/**").hasRole("ADMIN")
        );

        http.formLogin(Customizer.withDefaults());
        return http.build();
    }




    @Bean
    UserDetailsRepositoryReactiveAuthenticationManager authenticationManager(ReactiveUserDetailsService userDetailsService){
       UserDetailsRepositoryReactiveAuthenticationManager authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
       authenticationManager.setPasswordEncoder(new BCryptPasswordEncoder());
         return authenticationManager;
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
