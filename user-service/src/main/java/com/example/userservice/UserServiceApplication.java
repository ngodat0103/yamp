package com.example.userservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@SecurityScheme(
		type = SecuritySchemeType.OAUTH2,
		in = SecuritySchemeIn.HEADER,
		scheme = "bearer",
		description = "OAuth2 Bearer Token",
		name = "oauth2",
		flows = @OAuthFlows(
				authorizationCode = @OAuthFlow(
						authorizationUrl = "http://localhost:8000/api/v1/auth/oauth2/authorize",
						tokenUrl = "http://localhost:8000/api/v1/auth/oauth2/token",
						refreshUrl = "http://localhost:8000/api/v1/auth/oauth2/refresh"
				)
		)
)


@OpenAPIDefinition()


public class UserServiceApplication {


	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
