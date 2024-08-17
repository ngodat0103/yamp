package org.example.authservice.integrated_test;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.example.authservice.dto.AccountDto;
import org.example.authservice.dto.mapper.AccountMapper;
import org.example.authservice.persistence.entity.Account;
import org.example.authservice.persistence.repository.AccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Random;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class ControllerTest {
    @Autowired
    WebTestClient webTestClient;
    @Autowired
    AccountMapper accountMapper;
    private final ObjectMapper objectMapper =  JsonMapper.builder().disable(MapperFeature.USE_ANNOTATIONS).build();
    AccountDto accountDtoRequest = AccountDto.builder()
            .accountUuid(UUID.randomUUID())
            .username("testUser"+new Random().nextInt())
            .password("testPassword")
            .email("test@gmail.com")
            .build();

    AccountDto accountDtoResponse = AccountDto.builder()
            .username(accountDtoRequest.getUsername())
            .email(accountDtoRequest.getEmail())
            .accountUuid(accountDtoRequest.getAccountUuid())
            .password(null)
            .build();

    @Autowired
    private AccountRepository accountRepository ;

    @Test
    public void contextLoads() {
    }


    @AfterEach
    public void tearDown() {
        accountRepository.deleteAll();
    }

    @Test
    public void testRegisterAccountWhenNotFound() throws Exception {
        webTestClient.post()
                .uri("/account/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(objectMapper.writeValueAsString(accountDtoRequest))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(AccountDto.class)
                .isEqualTo(accountDtoResponse);
    }

    @Test
    public void testRegisterAccountWhenFound() throws Exception {
        Account account = accountMapper.mapToEntity(accountDtoRequest);
        accountRepository.save(account);

        webTestClient.post()
                .uri("/account/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(objectMapper.writeValueAsString(accountDtoRequest))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody()
                .jsonPath("$.message", containsString("Account already exists"));
    }

}
