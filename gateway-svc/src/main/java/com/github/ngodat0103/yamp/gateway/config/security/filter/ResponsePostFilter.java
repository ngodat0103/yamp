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
import java.time.Duration;
import java.time.Instant;

@Component
public class ResponsePostFilter implements GlobalFilter, Ordered {
    private static final Logger logger = LoggerFactory.getLogger(ResponsePostFilter.class);
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders requestHeader = exchange.getRequest().getHeaders();
        HttpHeaders ResponseHeader = exchange.getResponse().getHeaders();
        InetSocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();
        String correlationId = requestHeader.getFirst("X-Correlation-ID");
        String xResponseTime = requestHeader.getFirst("X-Response-Time");

        if (correlationId != null) {
            ResponseHeader.add("X-Correlation-ID", correlationId);
            logger.debug("{}: Updated the correlation id to the outbound: {}", remoteAddress,correlationId);
        }


        if(xResponseTime != null){
            Duration timeElapsedDuration = Duration.between(Instant.parse(xResponseTime), Instant.now());
            ResponseHeader.add("X-Response-Time",timeElapsedDuration.toString().substring(2));
            logger.debug("{}: Updated the response time to the outbound: {}", remoteAddress,timeElapsedDuration.toString().substring(2));
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}
