package com.github.ngodat0103.yamp.authsvc.vault;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Getter
@ConfigurationProperties(prefix = "hcp-vault")
@Configuration
@Setter
public class HcpVaultConfiguration{
    private String jwkSecretPath;
    private WebClient webClient;
    @Bean
    public AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientService clientService) {
        return new AuthorizedClientServiceOAuth2AuthorizedClientManager(clientRegistrationRepository, clientService);
    }

    @Bean
    WebClient webClient(AuthorizedClientServiceOAuth2AuthorizedClientManager oAuth2AuthorizedClientManager){
        ServletOAuth2AuthorizedClientExchangeFilterFunction exchangeFilterFunction = new ServletOAuth2AuthorizedClientExchangeFilterFunction(oAuth2AuthorizedClientManager);
        exchangeFilterFunction.setDefaultClientRegistrationId("hcp-vault-client");
        return WebClient.builder().
                apply(exchangeFilterFunction.oauth2Configuration())
                .build();
    }

}