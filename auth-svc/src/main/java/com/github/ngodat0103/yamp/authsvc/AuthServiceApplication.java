package com.github.ngodat0103.yamp.authsvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class AuthServiceApplication {
  public static void main(String[] args) {
    ApplicationContext ctx = SpringApplication.run(AuthServiceApplication.class, args);
    int stop = 0;
  }
}
