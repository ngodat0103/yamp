package org.example.authservice.integrated_test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.transaction.Transactional;
import org.example.authservice.dto.AccountDto;
import org.example.authservice.dto.RoleDto;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Random;
import java.util.UUID;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class ControllerTest {
    @Autowired
    WebTestClient webTestClient;
    private final ObjectMapper objectMapper =  JsonMapper.builder().disable(MapperFeature.USE_ANNOTATIONS).build();

    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void contextLoads() {
    }




    @Test
    @Order(2)
    public void testRegisterAccount() throws JsonProcessingException {
        RoleDto roleDto = new RoleDto("CUSTOMER","Customer role");
        webTestClient.post()
                .uri("/role/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(roleDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(RoleDto.class).isEqualTo(roleDto);
        AccountDto accountDto = AccountDto.builder()
                .accountUuid(UUID.randomUUID())
                .username("testUser"+new Random().nextInt())
                .password("testPassword")
                .email("test@gmail.com")
                .build();


        webTestClient.post()
                .uri("/account/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(objectMapper.writeValueAsString(accountDto))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(AccountDto.class).isEqualTo(accountDto);
    }

}
