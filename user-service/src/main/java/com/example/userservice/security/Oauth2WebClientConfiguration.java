package com.example.userservice.security;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import static com.example.userservice.constant.AuthServiceUri.AUTH_SVC_BASE;


// This class is used to configure the WebClient to use OAuth2 for authentication and authorization.

@Component
public class Oauth2WebClientConfiguration {

  //todo: Will implement using redis cache for further optimization
    @Bean
    WebClient webClient() {
      return WebClient.builder()
              .baseUrl(AUTH_SVC_BASE)
              .build();
    }
}
