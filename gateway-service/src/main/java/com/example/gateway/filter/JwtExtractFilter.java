package com.example.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
@Component
public class JwtExtractFilter implements GlobalFilter, Ordered {

    private final ReactiveJwtDecoder reactiveJwtDecoder;
    public JwtExtractFilter(ReactiveJwtDecoder reactiveJwtDecoder){
        this.reactiveJwtDecoder = reactiveJwtDecoder;
    }
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String jwtHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if(jwtHeader == null || !jwtHeader.startsWith("Bearer ")){
            return chain.filter(exchange);
        }
        String token = jwtHeader.substring(7);
        return reactiveJwtDecoder.decode(token).flatMap(jwt -> {
           ServerWebExchange reMutate = exchange.mutate().request( r -> r
                   .headers(h-> {
                       h.add("X-Account-Uuid", jwt.getClaim("X-Account-Uuid").toString());
                       h.remove(HttpHeaders.AUTHORIZATION);
                   }
                   )
           ).build();
           return chain.filter(reMutate);
        });

    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }
}
