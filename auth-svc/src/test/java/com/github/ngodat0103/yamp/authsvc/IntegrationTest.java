package com.github.ngodat0103.yamp.authsvc;


import com.github.ngodat0103.yamp.authsvc.dto.account.AccountRequestDto;
import com.github.ngodat0103.yamp.authsvc.dto.account.AccountResponseDto;
import com.github.ngodat0103.yamp.authsvc.dto.mapper.AccountMapper;
import com.github.ngodat0103.yamp.authsvc.persistence.repository.AccountRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("integration-test")
public class IntegrationTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    AccountMapper accountMapper;
    private final Random random = new Random();
    @Autowired
    private AccountRepository accountRepository;

    private final String TEMPLATE_DETAIL = "%s with %s: %s already exists";
    private final AccountRequestDto accountRequestDtoMock = AccountRequestDto.builder()
            .uuid(UUID.randomUUID().toString())
            .username("testUser"+random.nextInt())
            .password("testPassword")
            .email("test@gmail.com")
            .roleName("CUSTOMER")
            .build();
    private final AccountResponseDto accountResponseDtoMock = AccountResponseDto.builder()
            .username(accountRequestDtoMock.getUsername())
            .email(accountRequestDtoMock.getEmail())
            .uuid(accountRequestDtoMock.getUuid())
            .roleName(accountRequestDtoMock.getRoleName())
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
    @DisplayName("Given nothing when create account then return accountDto with 404")
    @Order(2)
    public void givenNothing_whenCreateAccount_thenReturnAccountDtoWith404() {
        var responseEntity =  testRestTemplate.postForEntity("/accounts", accountRequestDtoMock, ProblemDetail.class);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        var bodyResponse = responseEntity.getBody();
        Assertions.assertNotNull(bodyResponse);
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), bodyResponse.getStatus());
        Assertions.assertEquals("https://problems-registry.smartbear.com/not-found", bodyResponse.getType().toString());
        Assertions.assertEquals("Not found", bodyResponse.getTitle());
        Assertions.assertEquals("Role with roleName: CUSTOMER not found", bodyResponse.getDetail());
        Assertions.assertEquals("/accounts", Objects.requireNonNull(bodyResponse.getInstance()).toString());
    }

    @Test
    @DisplayName("Given username already exists when create account then return ProblemDetail with status 409")
    @Order(3)
    @Disabled("This test is disabled because it is not yet implemented")
    public void given_UsernameExists_whenCreateAccount_thenReturnProblemDetailWithStatus409() throws Exception {
        var responseBody = testRestTemplate.postForEntity("/accounts", accountRequestDtoMock, ProblemDetail.class);
        Assertions.assertEquals(HttpStatus.CONFLICT, responseBody.getStatusCode());
        var body = responseBody.getBody();
        Assertions.assertNotNull(body);
        Assertions.assertEquals(ProblemDetail.class, body.getClass());
        Assertions.assertEquals(body.getType().toString(),"https://problems-registry.smartbear.com/already-exists");
        Assertions.assertEquals(body.getTitle(),"Already exists");
        Assertions.assertEquals(body.getStatus(),HttpStatus.CONFLICT.value());
        Assertions.assertEquals(body.getDetail(), String.format(TEMPLATE_DETAIL, "Account", "username", accountRequestDtoMock.getUsername()));
    }
}
