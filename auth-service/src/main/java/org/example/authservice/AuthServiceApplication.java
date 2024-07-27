package org.example.authservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;


@SpringBootApplication
@OpenAPIDefinition
public class AuthServiceApplication {

    //fdsfsdfdsfdaffdfddsad
    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

}
