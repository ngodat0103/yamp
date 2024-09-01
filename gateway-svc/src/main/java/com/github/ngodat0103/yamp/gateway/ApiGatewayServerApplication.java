package com.github.ngodat0103.yamp.gateway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFluxSecurity
@EnableWebFlux
public class ApiGatewayServerApplication {
	public static void main(String[] args) {

//fdsf
		SpringApplication.run(ApiGatewayServerApplication.class, args);

    }

}
