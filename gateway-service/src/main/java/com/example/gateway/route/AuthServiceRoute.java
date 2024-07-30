package com.example.gateway.route;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import java.net.URI;
import java.time.Duration;

@Configuration
public class AuthServiceRoute {

    @Bean
    RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder){
        return routeLocatorBuilder.routes()
                .route( p-> p
                        .path("/auth/**")
                        .uri(URI.create("http://auth-service:8001"))

                )
                .build();
    }
}
