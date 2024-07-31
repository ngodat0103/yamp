//package com.example.gateway.route;
//
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.net.URI;
//import java.util.ArrayList;
//import java.util.List;
//
//@Configuration
//public class AuthServiceRoute {
//
//    @Bean
//    RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder){
//        RouteLocator routeLocator =  routeLocatorBuilder.routes()
//                .route( p-> p
//                        .path("/api/v1/auth/**")
//                        .uri(URI.create("http://auth-service:8001"))
//
//                )
//                .route( p-> p
//                        .path("/api/v1/user/**")
//                        .uri(URI.create("http://user-service:8002"))
//
//                )
//                .build();
//
//
//
//        return routeLocator;
//    }
//}
