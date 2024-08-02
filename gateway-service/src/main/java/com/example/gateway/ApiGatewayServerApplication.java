package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.web.reactive.config.EnableWebFlux;



@SpringBootApplication
@EnableWebFluxSecurity
@EnableWebFlux
public class ApiGatewayServerApplication {
	public static void main(String[] args) {
	ApplicationContext ctx =  SpringApplication.run(ApiGatewayServerApplication.class, args);

    }

}
