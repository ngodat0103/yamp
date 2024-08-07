package com.example.gateway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.config.GatewayResilience4JCircuitBreakerAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.util.function.Consumer;

@SpringBootApplication
@EnableWebFluxSecurity
@EnableWebFlux
public class ApiGatewayServerApplication {
	public static void main(String[] args) {
	ApplicationContext ctx =  SpringApplication.run(ApiGatewayServerApplication.class, args);

    }

}
