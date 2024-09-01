package com.example.yamp.usersvc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.encrypt.TextEncryptor;

@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class UserServiceApplication {

//fdsfsd

	public static void main(String[] args) {
	  ApplicationContext ctx =  SpringApplication.run(UserServiceApplication.class, args);

	}

}
