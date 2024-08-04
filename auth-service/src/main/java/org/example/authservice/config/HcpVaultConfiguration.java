package org.example.authservice.config;

import com.nimbusds.oauth2.sdk.ParseException;
import lombok.Getter;
import lombok.Setter;
import org.example.authservice.vault.HcpVault;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Map;

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