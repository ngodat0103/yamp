package com.example.yamp.usersvc.service.client;

import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

// This class is used to configure the WebClient to use OAuth2 for authentication and authorization.

@Component
public class AuthSvcWebClient {
  public static final String AUTH_SVC_BASE = "http://auth-svc:8001/api/v1/auth";

  @Bean
  WebClient webClient(OAuth2AuthorizedClientManager authorizedClientManager) {
    ServletOAuth2AuthorizedClientExchangeFilterFunction exchangeFilterFunction =
        new ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
    exchangeFilterFunction.setDefaultClientRegistrationId("user-service");
    exchangeFilterFunction.setDefaultOAuth2AuthorizedClient(true);
    exchangeFilterFunction.oauth2Configuration();
    return WebClient.builder()
        .apply(exchangeFilterFunction.oauth2Configuration())
        .baseUrl(AUTH_SVC_BASE)
        .build();
  }
}
