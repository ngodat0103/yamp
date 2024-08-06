package com.example.gateway.authTZ;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

    @Bean
    WebClient webClient(ReactiveOAuth2AuthorizedClientManager oAuth2ReactiveAuthorizationManagers ){
        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth2Client = new ServerOAuth2AuthorizedClientExchangeFilterFunction(oAuth2ReactiveAuthorizationManagers);
        oauth2Client.setDefaultOAuth2AuthorizedClient(true);
        oauth2Client.setDefaultClientRegistrationId("gateway-service");
        return WebClient.builder()
                .filter(oauth2Client)
                .build();
    }
}
