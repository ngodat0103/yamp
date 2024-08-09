package org.example.productsvc;

import io.swagger.v3.oas.annotations.OpenAPI31;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.encrypt.TextEncryptor;

@SpringBootApplication
@OpenAPIDefinition
public class ProductSvcApplication {
    public static void main(String[] args) {
       ApplicationContext ctx =  SpringApplication.run(ProductSvcApplication.class, args);
        int stop = 0 ;
    }

}
