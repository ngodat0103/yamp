package com.example.yamp.usersvc.security;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


// This class is used to configure the WebClient to use OAuth2 for authentication and authorization.

@Component
public class Oauth2WebClientConfiguration {
  public static final String AUTH_SVC_BASE = "http://auth-svc:8001/api/v1/auth";

  //todo: Will implement using redis cache for further optimization
    @Bean
    WebClient webClient() {
      return WebClient.builder()
              .baseUrl(AUTH_SVC_BASE)
              .build();
    }
}
