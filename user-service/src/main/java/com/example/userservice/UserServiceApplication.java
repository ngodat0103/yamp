package com.example.userservice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;



@SpringBootApplication
public class UserServiceApplication {


	public static void main(String[] args) {
	 ApplicationContext ctx =  SpringApplication.run(UserServiceApplication.class, args);
	}

}
