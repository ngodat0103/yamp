package com.example.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtExtractFilter implements GlobalFilter {

    private final ReactiveJwtDecoder reactiveJwtDecoder;
    public JwtExtractFilter(ReactiveJwtDecoder reactiveJwtDecoder){
        this.reactiveJwtDecoder = reactiveJwtDecoder;
    }
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String jwtHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if(jwtHeader == null || !jwtHeader.startsWith("Bearer ")){
            return chain.filter(exchange);
        }
        String token = jwtHeader.substring(7);
        return reactiveJwtDecoder.decode(token).flatMap(jwt -> {
           ServerWebExchange reMutate = exchange.mutate().request( r -> r
                   .headers(h-> h
                           .add("x-account-uuid", jwt.getClaim("x-account-uuid").toString())
                   )
           ).build();
           return chain.filter(reMutate);
        });

    }
}
