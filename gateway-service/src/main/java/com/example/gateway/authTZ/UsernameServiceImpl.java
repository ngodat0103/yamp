package com.example.gateway.authTZ;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

@Component
public class UsernameServiceImpl implements ReactiveUserDetailsService {

    final WebClient webClient ;
    private final ReactiveJwtDecoder reactiveJwtDecoder;
    public UsernameServiceImpl(WebClient webClient, ReactiveJwtDecoder reactiveJwtDecoder) {
        this.webClient = webClient;
        this.reactiveJwtDecoder = reactiveJwtDecoder;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString("http://auth-service:8001/api/v1/auth/account/userinfo");
        uriComponentsBuilder.queryParam("username",username);
        URI uri = uriComponentsBuilder.build().toUri();
        return webClient.get()
                .uri(uri)
                .retrieve()
                .toBodilessEntity()
                .onErrorResume(WebClientResponseException.class,e -> {
                    throw new UsernameNotFoundException("User not found");
                })
                .mapNotNull(responseEntity -> responseEntity.getHeaders().getFirst("X-User-Info"))
                .flatMap(reactiveJwtDecoder::decode)
                .map(jwt -> {
                    String password = jwt.getClaimAsString("password");
                    List<String> roles = jwt.getClaimAsStringList("roles");
                    List<SimpleGrantedAuthority> authorityList = roles.stream()
                            .map(SimpleGrantedAuthority::new)
                            .toList();
                    return User.withUsername(username)
                            .password(password)
                            .authorities(authorityList)
                            .build();
                });

    }
}
