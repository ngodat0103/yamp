package com.github.ngodat0103.yamp.authsvc;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@OpenAPIDefinition
public class AuthServiceApplication {

    public static void main(String[] args) {

        // fsdfdsfsffdsffdsffdsffdsffdsfdfdfd
     ApplicationContext context=  SpringApplication.run(AuthServiceApplication.class, args);


     ClassLoader classLoader = context.getClassLoader();
     int stop = 0 ;
    }

}