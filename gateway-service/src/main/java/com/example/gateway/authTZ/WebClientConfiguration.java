package com.example.gateway.authTZ;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.web.reactive.function.client.WebClient;



// This class is used to configure the WebClient bean to be used in the UserDetailServiceImpl class
// to make a request to the auth-service to get the user information
@Configuration
public class WebClientConfiguration {

    @Bean
    WebClient webClient(ServerOAuth2AuthorizedClientRepository authorizedClientManager,
                        ReactiveClientRegistrationRepository clientRegistrationRepository
                        ){
        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth2Client = new ServerOAuth2AuthorizedClientExchangeFilterFunction(
                clientRegistrationRepository,
                authorizedClientManager
        );
        oauth2Client.setDefaultOAuth2AuthorizedClient(true);
        oauth2Client.setDefaultClientRegistrationId("gateway-service");
        return WebClient.builder()
                .filter(oauth2Client)
                .build();
    }
}
