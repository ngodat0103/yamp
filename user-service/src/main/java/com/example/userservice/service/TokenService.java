package com.example.userservice.service;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    private final ClientRegistration clientRegistration;
    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    public TokenService(InMemoryClientRegistrationRepository inMemoryClientRegistrationRepository,
                        OAuth2AuthorizedClientService oAuth2AuthorizedClientService) {
        clientRegistration = inMemoryClientRegistrationRepository.findByRegistrationId("user-service");
        this.oAuth2AuthorizedClientService = oAuth2AuthorizedClientService;
    }
    public String getAccessToken(){
        OAuth2AuthorizedClient oAuth2AuthorizedClient = oAuth2AuthorizedClientService.loadAuthorizedClient(clientRegistration.getRegistrationId(), "user-service");
        return oAuth2AuthorizedClient.getAccessToken().getTokenValue();
    }
}
