package com.example.yamp.usersvc.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.ClientAuthorizationException;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
@Transactional
@Disabled(value = "Temporarily disabled")
class ControllerTest {

    @Autowired
    private WebTestClient webTestClient;
    @Test
    void contextLoad() {
    }

    @Test
    @Order(1)
    @DisplayName("Test register account")
    void testRegisterAccount() throws IOException {
        ClassPathResource resource = new ClassPathResource("integration/register-body.json");
        webTestClient.post()
                .uri("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(resource.getInputStream().readAllBytes() )
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    @Order(2)
    @DisplayName("Test get account but not include jwt token")
    void testGetAccountWithoutJwtToken() {
        webTestClient.get()
                .uri("/get-me")
                .exchange()
                .expectStatus().isUnauthorized();
    }

//    @Test
//    @Order(3)
//    @DisplayName("Test get account but include jwt token")
//    @WithSecurityContext(factory = WithMockJwtSecurityContextFactory.class)
//    public void testGetAccountWithJwtToken(OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager) {
//        ClientRegistration clientRegistration = new ClientRe
//        var exchangeFilterFunction = new ServletOAuth2AuthorizedClientExchangeFilterFunction(oAuth2AuthorizedClientManager);
//        webTestClient.mutate().filter(exchangeFilterFunction).build()
//                .get()
//                .uri("/get-me")
//                .exchange()
//                .expectStatus().isOk();
//
//    }


}
