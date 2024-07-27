package com.example.gateway.gatewayserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.kubernetes.discovery.KubernetesDiscoveryClient;
import org.springframework.context.ApplicationContext;


@EnableDiscoveryClient
@SpringBootApplication
public class ApiGatewayServerApplication {
	public static void main(String[] args) {

ApplicationContext ctx = SpringApplication.run(ApiGatewayServerApplication.class, args);
	DiscoveryClient discoveryClient  =ctx.getBean(KubernetesDiscoveryClient.class);
	discoveryClient.getServices().stream().forEach(System.out::println);
	int stop = 0 ;
	}



}
