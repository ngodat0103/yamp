//package com.example.gateway.gatewayserver.config.security;
//
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.core.Ordered;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//
//public class ExtractJwtFilter extends OncePerRequestFilter {
//
//    private final JwtDecoder jwtDecoder;
//    public ExtractJwtFilter(JwtDecoder jwtDecoder) {
//        this.jwtDecoder = jwtDecoder;
//    }
//
//    @Override
//    public int getOrder() {
//        return 0;
//    }
//
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
//    }
//}
