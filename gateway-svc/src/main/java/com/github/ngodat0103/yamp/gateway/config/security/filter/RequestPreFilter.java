package com.github.ngodat0103.yamp.gateway.config.security.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.time.Instant;
import java.util.UUID;

@Component
public class RequestPreFilter implements GlobalFilter, Ordered {
    private static final Logger logger = LoggerFactory.getLogger(RequestPreFilter.class);
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders rHeader = exchange.getRequest().getHeaders();
        InetSocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();
        String identity = remoteAddress != null ? remoteAddress.toString() : "unknown";
        if(isCorrelationIdPresent(rHeader)) {
            logger.debug("{}: Correlation ID found in: {}.", identity,rHeader.get("X-Correlation-ID"));
            return chain.filter(exchange);
        }

        logger.debug("{}: No correlation ID found. Generating a new one.", identity);
        String correlationId = generateCorrelationId();
        exchange.mutate().request(r->
                r.headers(h ->
                {
                    h.add("X-Response-Time", Instant.now().toString());
                    h.add("X-Correlation-ID", correlationId);
                }
        ));
        logger.debug("{}: Generated Correlation ID: {}",identity, correlationId);
        return chain.filter(exchange);
    }
    
    private boolean isCorrelationIdPresent(HttpHeaders headers) {
        return headers.containsKey("X-Correlation-ID");
    }
    String generateCorrelationId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }
}
