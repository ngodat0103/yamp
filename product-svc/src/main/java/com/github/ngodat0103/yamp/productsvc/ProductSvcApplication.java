package com.github.ngodat0103.yamp.productsvc;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;



@SpringBootApplication
@OpenAPIDefinition
public class ProductSvcApplication {
    public static void main(String[] args) {
       ApplicationContext ctx =  SpringApplication.run(ProductSvcApplication.class, args);
    }

}
