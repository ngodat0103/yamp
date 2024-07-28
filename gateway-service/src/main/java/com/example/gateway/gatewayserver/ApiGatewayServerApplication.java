package com.example.gateway.gatewayserver;

import io.kubernetes.client.openapi.apis.CoreV1Api;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.kubernetes.client.loadbalancer.KubernetesClientServiceInstanceMapper;
import org.springframework.cloud.kubernetes.client.loadbalancer.KubernetesClientServicesListSupplier;
import org.springframework.cloud.kubernetes.commons.KubernetesNamespaceProvider;
import org.springframework.cloud.kubernetes.commons.discovery.KubernetesDiscoveryProperties;
import org.springframework.cloud.kubernetes.commons.loadbalancer.ConditionalOnKubernetesLoadBalancerServiceModeEnabled;
import org.springframework.cloud.kubernetes.discovery.KubernetesDiscoveryClient;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;


@EnableDiscoveryClient
@SpringBootApplication
public class ApiGatewayServerApplication {
	public static void main(String[] args) {

ApplicationContext ctx = SpringApplication.run(ApiGatewayServerApplication.class, args);
	DiscoveryClient discoveryClient  =ctx.getBean(KubernetesDiscoveryClient.class);
	discoveryClient.getServices().forEach(System.out::println);
    }

	@Bean
	@ConditionalOnKubernetesLoadBalancerServiceModeEnabled
	ServiceInstanceListSupplier kubernetesServicesListSupplier(Environment environment, CoreV1Api coreV1Api,
															   KubernetesClientServiceInstanceMapper mapper, KubernetesDiscoveryProperties discoveryProperties,
															   KubernetesNamespaceProvider kubernetesNamespaceProvider, ConfigurableApplicationContext context) {
		return ServiceInstanceListSupplier.builder()
				.withBase(new KubernetesClientServicesListSupplier(environment, mapper, discoveryProperties, coreV1Api,
						kubernetesNamespaceProvider))
				.withCaching()
				.withRequestBasedStickySession()
				.build(context);
	}



}
