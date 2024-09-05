package com.github.ngodat0103.yamp.productsvc.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class PreAuthorizeFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(@NonNull  HttpServletRequest request,@NonNull HttpServletResponse response, @NonNull FilterChain filterChain) {
        String principal = request.getHeader("X-Principal");
        if(principal==null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new PreAuthenticatedAuthenticationToken(principal,null));
        SecurityContextHolder.setContext(context);
    }
}
