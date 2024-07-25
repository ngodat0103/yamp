package com.example.gateway.gatewayserver.config.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class ExtractJwtFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;
    public ExtractJwtFilter(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        String token = request.getHeader("Authorization");
        if(token != null && token.startsWith("Bearer ")){
            token = token.substring(7);
            jwtDecoder.decode(token);
        }

    }
}
