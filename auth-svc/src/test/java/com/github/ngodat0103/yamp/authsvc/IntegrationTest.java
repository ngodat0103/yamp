package com.github.ngodat0103.yamp.authsvc;


import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.github.ngodat0103.yamp.authsvc.dto.AccountDto;
import com.github.ngodat0103.yamp.authsvc.dto.mapper.AccountMapper;
import org.junit.jupiter.api.*;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.Random;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("integration-test")
@Disabled("This test is disabled because it is not yet implemented")
public class IntegrationTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    AccountMapper accountMapper;
    private final Random random = new Random();


    private final String TEMPLATE_DETAIL = "%s with %s: %s already exists";
    AccountDto accountDtoRequest = AccountDto.builder()
            .uuid(UUID.randomUUID().toString())
            .username("testUser"+random.nextInt())
            .password("testPassword")
            .email("test@gmail.com")
            .roleName("CUSTOMER")
            .build();
    AccountDto accountDtoResponse = AccountDto.builder()
            .username(accountDtoRequest.getUsername())
            .email(accountDtoRequest.getEmail())
            .uuid(accountDtoRequest.getUuid())
            .password(null)
            .roleName("CUSTOMER")
            .build();

    @BeforeEach
    void setUp() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        this.testRestTemplate.getRestTemplate().setInterceptors(Collections.singletonList((request, body, execution) -> {
            request.getHeaders().addAll(headers);
            return execution.execute(request, body);
        }));
    }
    @Test
    @DisplayName("context loads")
    @Order(1)
    public void contextLoads() {
    }

    @Test
    @DisplayName("Given nothing when create account then return accountDto with 201")
    @Order(2)
    public void givenNothing_whenCreateAccount_thenReturnAccountDtoWith201() {
        var responseEntity =  testRestTemplate.postForEntity("/accounts", accountDtoRequest, AccountDto.class);
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        var body = responseEntity.getBody();
        Assertions.assertNotNull(body);
        Assertions.assertEquals(accountDtoResponse, body);
    }

    @Test
    @DisplayName("Given username already exists when create account then return ProblemDetail with status 409")
    @Order(3)
    public void given_UsernameExists_whenCreateAccount_thenReturnProblemDetailWithStatus409() throws Exception {
        var responseBody = testRestTemplate.postForEntity("/accounts", accountDtoRequest, ProblemDetail.class);
        Assertions.assertEquals(HttpStatus.CONFLICT, responseBody.getStatusCode());
        var body = responseBody.getBody();
        Assertions.assertNotNull(body);
        Assertions.assertEquals(ProblemDetail.class, body.getClass());
        Assertions.assertEquals(body.getType().toString(),"https://problems-registry.smartbear.com/already-exists");
        Assertions.assertEquals(body.getTitle(),"Already exists");
        Assertions.assertEquals(body.getStatus(),HttpStatus.CONFLICT.value());
        Assertions.assertEquals(body.getDetail(), String.format(TEMPLATE_DETAIL, "Account", "username", accountDtoRequest.getUsername()));
    }
}
