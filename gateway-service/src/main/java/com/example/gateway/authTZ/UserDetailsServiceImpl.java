package com.example.gateway.authTZ;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.ConnectException;
import java.net.URI;
import java.util.List;

@Component
public class UserDetailsServiceImpl implements ReactiveUserDetailsService {

    final WebClient webClient ;
    private final ReactiveJwtDecoder reactiveJwtDecoder;
    public UserDetailsServiceImpl(WebClient webClient, ReactiveJwtDecoder reactiveJwtDecoder) {
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
                .doOnError(WebClientResponseException.class,e -> {
                    if(e.getStatusCode().equals(HttpStatus.NOT_FOUND))
                        throw new UsernameNotFoundException("User not found");
                    else if(e.getStatusCode().is5xxServerError())
                        throw new InternalAuthenticationServiceException("Internal server error");
        })
                .doOnError(WebClientRequestException.class, e -> {
                    throw new InternalAuthenticationServiceException("Internal server error");
                })
                .mapNotNull(responseEntity -> responseEntity.getHeaders().getFirst("X-User-Info"))
                .flatMap(reactiveJwtDecoder::decode)
                .map(jwt -> {
                    String password = jwt.getClaimAsString("password");
                    List<String> roles = jwt.getClaimAsStringList("roles");
                    String accountUuid = jwt.getClaimAsString("accountUuid");
                    List<SimpleGrantedAuthority> authorityList = roles.stream()
                            .map(SimpleGrantedAuthority::new)
                            .toList();
                    return User.withUsername(accountUuid)
                            .password(password)
                            .authorities(authorityList)
                            .build();
                });


    }
}
