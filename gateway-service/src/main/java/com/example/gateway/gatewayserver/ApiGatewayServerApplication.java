package com.example.gateway.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.server.mvc.handler.ProxyExchangeHandlerFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.addRequestHeader;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.removeRequestHeader;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.https;
import static org.springframework.web.servlet.function.RouterFunctions.route;

@SpringBootApplication
public class ApiGatewayServerApplication {

	public static void main(String[] args) {


	SpringApplication.run(ApiGatewayServerApplication.class, args);
	}
	@Bean
	RouterFunction<ServerResponse> routerFunction(){
		return route().
				before(addRequestHeader("x-account-uuid","this is account uuid")).
				GET("/account/**",http("http://auth-service:8001")).
				POST("/account/**",http("http://auth-service:8001")).
				build();
	}

}
