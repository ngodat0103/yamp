package com.example.userservice.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfiguration {
    @Bean
    SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {

        // disable all security features for internal communication microservices
        http.cors(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);


        http.oauth2ResourceServer(resource ->
                resource.jwt(jwt->jwt.decoder(jwtDecoder())));;


        http.sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests((authorizeRequests) ->
                authorizeRequests
                        .requestMatchers(HttpMethod.POST,"/get-me").authenticated()
                        .anyRequest().permitAll());
        http.headers(header -> header.addHeaderWriter(new TraceHeaderWriter()));
        return http.build();
    }

    JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withJwkSetUri("http://auth-svc:8001/api/v1/auth/oauth2/jwks").build();
    }


    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



}
