package com.github.ngodat0103.yamp.authsvc.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.resource.authentication.DelegatingJwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain oauth2FilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        http.securityMatcher("/oauth2/**");
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc(Customizer.withDefaults());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));
        http.exceptionHandling(e -> e.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")));
        return http.build();
    }
    @Bean
    public SecurityFilterChain loginFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(Customizer.withDefaults());
        http.securityMatcher("/login");
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/login").permitAll()
        );
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.NEVER));
       return http.build();
    }


    @Bean
    SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);
        http.securityMatcher("/accounts/**","/roles/**","/ui-docs/**","/api-docs/**");
        http.oauth2ResourceServer(resource -> resource.jwt(jwt -> jwt.jwtAuthenticationConverter(getJwtAuthenticationConverter())));
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/ui-docs/**").permitAll()
                .requestMatchers("/api-docs/**").permitAll()
                .anyRequest().authenticated()
        );
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }


    @Bean
    AuthorizationServerSettings authorizationServerSettings(){
        return AuthorizationServerSettings.builder().build();
    }


    @Bean
    PasswordEncoder passwordEncoder(){
        Map<String,PasswordEncoder> passwordEncoderMap = new HashMap<>();
        passwordEncoderMap.put("bcrypt",new BCryptPasswordEncoder());
        passwordEncoderMap.put("noop", NoOpPasswordEncoder.getInstance()); // I use this for easy development
        return new DelegatingPasswordEncoder("bcrypt",passwordEncoderMap);
    }



    public JwtAuthenticationConverter getJwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter converterForRoles = new JwtGrantedAuthoritiesConverter();
        converterForRoles.setAuthorityPrefix("ROLE_");
        converterForRoles.setAuthoritiesClaimName("role");
        JwtGrantedAuthoritiesConverter converterForScope = new JwtGrantedAuthoritiesConverter();


        DelegatingJwtGrantedAuthoritiesConverter delegatingJwtGrantedAuthoritiesConverter = new DelegatingJwtGrantedAuthoritiesConverter(converterForRoles,converterForScope);
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(delegatingJwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }



}
