package com.example.yamp.usersvc.integration;


import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
@Transactional
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class ControllerTest {

    @Autowired
    private WebTestClient webTestClient;
    @Test
    public void contextLoad() {
    }
    @Test
    @Order(1)
    @DisplayName("given nothing when register account then return 201")
    public void givenNotAuthenticate_whenRegisterAccount_thenReturn401() throws IOException {
        ClassPathResource resource = new ClassPathResource("integration/register-body.json");
        webTestClient.post()
                .uri("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(resource.getInputStream().readAllBytes() )
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CREATED);
    }
    @Test
    @Order(2)
    @DisplayName("Given already has account when register then return 409")
    public void givenAlreadyHasAccount_whenRegister_thenReturn409() throws IOException {
        ClassPathResource resource = new ClassPathResource("integration/register-body.json");
        webTestClient.post()
                    .uri("/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(resource.getInputStream().readAllBytes())
                    .exchange()
                    .expectStatus().isEqualTo(HttpStatus.CONFLICT);
    }
}
