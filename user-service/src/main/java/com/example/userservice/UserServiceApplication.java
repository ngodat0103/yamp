package com.example.userservice;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.encrypt.TextEncryptor;


@SpringBootApplication
public class UserServiceApplication {


	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	public static void main(String[] args) {
	 ApplicationContext ctx =  SpringApplication.run(UserServiceApplication.class, args);

		TextEncryptor textEncryptor = ctx.getBean(TextEncryptor.class);

	}

}
