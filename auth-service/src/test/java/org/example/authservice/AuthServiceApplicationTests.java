package org.example.authservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.authservice.dto.AccountDto;
import org.example.authservice.entity.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthServiceApplicationTests {
    @Autowired
    WebTestClient webTestClient;

    @Test
    void contextLoads() throws JsonProcessingException {

    }


//
//    @Test
//    void register () throws Exception {
//        Account account = new Account("test","123456789");
//        webTestClient.post()
//                .uri("/account/register")
//                .bodyValue(account)
//                .exchange()
//                .expectStatus().isCreated()
//                .expectBody(AccountDto.class);
//
//    }


}
