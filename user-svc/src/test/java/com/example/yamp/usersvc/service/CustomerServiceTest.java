package com.example.yamp.usersvc.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.example.yamp.usersvc.cache.AuthSvcRepository;
import com.example.yamp.usersvc.dto.customer.CustomerDto;
import com.example.yamp.usersvc.dto.mapper.CustomerMapper;
import com.example.yamp.usersvc.dto.mapper.CustomerMapperImpl;
import com.example.yamp.usersvc.persistence.entity.Account;
import com.example.yamp.usersvc.persistence.entity.Customer;
import com.example.yamp.usersvc.persistence.repository.CustomerRepository;
import com.example.yamp.usersvc.service.impl.CustomerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;
import javax.security.auth.login.AccountNotFoundException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("unit-test")
@Disabled(value = "still bug")
public class CustomerServiceTest {

  @Mock private CustomerRepository customerRepository;
  @Mock private AuthSvcRepository authSvcRepository;
  @InjectMocks private CustomerServiceImpl customerServiceImpl;
  private MockWebServer mockWebServer;
  private static final String firstNameMock = "John";
  private static final String lastNameMock = "Doe";
  private static final String usernameMock = "testUser";
  private static final String emailMock = "example@gmail.com";
  private static final UUID customerUuidMock =
      UUID.fromString("1a35d863-0cd9-4bc1-8cc4-f4cddca97721");
  private final ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  void setUp() {
    this.mockWebServer = new MockWebServer();
    URI mockBaseUri = this.mockWebServer.url("/").uri();
    WebClient webClient = WebClient.builder().baseUrl(mockBaseUri.toString()).build();
    CustomerMapper customerMapper = new CustomerMapperImpl();
    this.customerServiceImpl =
        new CustomerServiceImpl(customerRepository, authSvcRepository, customerMapper, webClient);
    Customer customerMockResponse = new Customer();
    customerMockResponse.setCustomerUuid(customerUuidMock);
    customerMockResponse.setFirstName(firstNameMock);
    customerMockResponse.setLastName(lastNameMock);
  }

  @Test
  @DisplayName("Get customer details")
  @WithMockUser(username = "1a35d863-0cd9-4bc1-8cc4-f4cddca97721")
  @Disabled(value = "auth-svc not implement")
  void givenCustomerExists_whenGetCustomer_thenReturnCustomerDto()
      throws AccountNotFoundException, IOException {
    Customer customer = new Customer();
    customer.setCustomerUuid(customerUuidMock);
    customer.setFirstName(firstNameMock);
    customer.setLastName(lastNameMock);
    given(customerRepository.findCustomerByCustomerUuid(customerUuidMock))
        .willReturn(Optional.of(customer));

    Account accountMock = new Account();
    accountMock.setUuid(customerUuidMock);
    accountMock.setUsername(usernameMock);
    accountMock.setEmail(emailMock);

    mockWebServer.enqueue(
        new MockResponse()
            .setResponseCode(200)
            .addHeader("Content-Type", "application/json")
            .setBody(objectMapper.writeValueAsString(accountMock)));
    CustomerDto customerDto = customerServiceImpl.getCustomer(customerUuidMock);
    assertThat(customerDto).isNotNull();
    assertThat(customerDto.getAccountDto().uuid()).isEqualTo(customerUuidMock);
    assertThat(customerDto.getAccountDto().username()).isEqualTo(usernameMock);
    assertThat(customerDto.getAccountDto().email()).isEqualTo(emailMock);
    assertThat(customerDto.getFirstName()).isEqualTo(firstNameMock);
    assertThat(customerDto.getLastName()).isEqualTo(lastNameMock);
    then(customerRepository).should().findCustomerByCustomerUuid(customerUuidMock);
  }
}
