package com.example.userservice.filter;
import com.example.userservice.constant.Constant;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class JwtGeneratorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null){
            SecretKey secretKey = Keys.hmacShaKeyFor(Constant.SECRET_KEY.getBytes(StandardCharsets.UTF_8));
            String jwt = Jwts.builder()
                    .issuer("User-service")
                    .subject("JWT for user-service")
                    .claim("username",authentication.getName())
                    .claim("authorities",populateAuthorities(authentication.getAuthorities()))
                    .issuedAt(new Date())
                    .expiration(new Date(new Date().getTime()+3600000))
                    .signWith(secretKey)
                    .compact();
            response.setHeader(Constant.JWT_HEADER,jwt);
        }
        filterChain.doFilter(request,response);
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> collections){
        Set<String> authoritiesSet = new HashSet<>();
        for( GrantedAuthority grantedAuthority : collections)
            authoritiesSet.add(grantedAuthority.getAuthority());
        return String.join(",",authoritiesSet);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/api/v1/user/login");
    }
}
