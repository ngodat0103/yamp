package com.example.gateway.gatewayserver.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.addRequestHeader;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.removeRequestHeader;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.web.servlet.function.RouterFunctions.route;

@Configuration
public class AuthServiceRoute {
    private static final String AUTH_SERVICE = "http://auth-service:8001";
    	@Bean
        RouterFunction<ServerResponse> loginFunction(){
		return route().
                GET("/auth/login",http(AUTH_SERVICE)).
                POST("/auth/login",http(AUTH_SERVICE)).
                GET("/auth/oauth2/authorize",http(AUTH_SERVICE)).
                POST("/auth/oauth2/authorize",http(AUTH_SERVICE)).
                POST("/auth/oauth2/token",http(AUTH_SERVICE)).
                build();
	}
    @Bean RouterFunction<ServerResponse> infoFunction(JwtDecoder jwtDecoder){
        return route().
                GET("/auth/role/**",http(AUTH_SERVICE)).
                before(request -> {
                    String authorizationHeader = request.headers().firstHeader("Authorization");
                    if(authorizationHeader != null){
                        String token = authorizationHeader.replace("Bearer ","");
                        String xAccountUuid = jwtDecoder.decode(token).getClaim("x-account-uuid");
                        return addRequestHeader("x-account-uuid",xAccountUuid).apply(request);
                    }
                    return request;

                }).
                before(removeRequestHeader("Authorization")).
                build();
    }
}
