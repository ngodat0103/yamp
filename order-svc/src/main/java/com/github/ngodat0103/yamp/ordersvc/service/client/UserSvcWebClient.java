package com.github.ngodat0103.yamp.ordersvc.service.client;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class UserSvcWebClient {
    private static final String USER_SVC_BASE = "http://user-svc:8002/api/v1/user";

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
            .baseUrl(USER_SVC_BASE)
            .build();
    }
}
