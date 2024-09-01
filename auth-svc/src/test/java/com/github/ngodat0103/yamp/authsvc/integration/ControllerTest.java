package com.github.ngodat0103.yamp.authsvc.integration;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import com.github.ngodat0103.yamp.authsvc.dto.AccountDto;
import com.github.ngodat0103.yamp.authsvc.dto.mapper.AccountMapper;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.Account;
import com.github.ngodat0103.yamp.authsvc.persistence.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Random;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;


@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
@Transactional
public class ControllerTest {
    @Autowired
    WebTestClient webTestClient;
    @Autowired
    AccountMapper accountMapper;
    private final ObjectMapper objectMapper =  JsonMapper.builder().disable(MapperFeature.USE_ANNOTATIONS).build();
    AccountDto accountDtoRequest = AccountDto.builder()
            .accountUuid(UUID.randomUUID().toString())
            .username("testUser"+new Random().nextInt())
            .password("testPassword")
            .email("test@gmail.com")
            .roleName("CUSTOMER")
            .build();
    AccountDto accountDtoResponse = AccountDto.builder()
            .username(accountDtoRequest.getUsername())
            .email(accountDtoRequest.getEmail())
            .accountUuid(accountDtoRequest.getAccountUuid())
            .password(null)
            .roleName("CUSTOMER")
            .build();

    @Autowired
    private AccountRepository accountRepository ;

    @Test
    @DisplayName("context loads")
    public void contextLoads() {
    }



    @Test
    @DisplayName("Register account")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Disabled("Temporarily disabled for not handling the security context")
    public void givenAccountDto_whenRegister_thenReturnAccountDto() throws Exception {
        webTestClient.post()
                .uri("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(objectMapper.writeValueAsString(accountDtoRequest))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(AccountDto.class)
                .isEqualTo(accountDtoResponse);
    }

    @Test
    @DisplayName("Register Account but conflict")
    @WithMockUser(username = "admin", authorities = {"SCOPE_auth-svc.write"})
    @Disabled("Temporarily disabled for not handling the security context")
    public void givenAccountDto_whenRegister_thenReturnConflictException() throws Exception {
        Account account = accountMapper.mapToEntity(accountDtoRequest);
        accountRepository.save(account);


        webTestClient.post()
                .uri("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(objectMapper.writeValueAsString(accountDtoRequest))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody()
                .jsonPath("$.message", containsString("Account already exists"));
    }

}
