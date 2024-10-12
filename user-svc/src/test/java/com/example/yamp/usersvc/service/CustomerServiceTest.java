package com.example.yamp.usersvc.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.any;

import com.example.yamp.usersvc.cache.AuthSvcRepository;
import com.example.yamp.usersvc.dto.customer.CustomerDto;
import com.example.yamp.usersvc.dto.customer.CustomerRegisterDto;
import com.example.yamp.usersvc.dto.mapper.CustomerMapper;
import com.example.yamp.usersvc.dto.mapper.CustomerMapperImpl;
import com.example.yamp.usersvc.exception.NotFoundException;
import com.example.yamp.usersvc.persistence.entity.Account;
import com.example.yamp.usersvc.persistence.entity.Customer;
import com.example.yamp.usersvc.persistence.repository.CustomerRepository;
import com.example.yamp.usersvc.service.impl.CustomerServiceImpl;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.BasicJsonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("unit-test")
@Disabled(value = "auth-svc not update")
public class CustomerServiceTest {

  @Mock private CustomerRepository customerRepository;
  @Mock private AuthSvcRepository authSvcRepository;
  @InjectMocks private CustomerServiceImpl customerServiceImpl;

  private MockWebServer mockWebServer;
  private final BasicJsonTester jsonTester = new BasicJsonTester(getClass());
  private static final String username = "testUser";
  private static final String password = "password";
  private static final String email = "example@gmail.com";
  private static final String firstName = "John";
  private static final String lastName = "Doe";
  private static final UUID customerUuid = UUID.fromString("1a35d863-0cd9-4bc1-8cc4-f4cddca97721");
  private CustomerRegisterDto customerRegisterDto;
  private final ClassLoader classLoader = getClass().getClassLoader();
  private CustomerMapper customerMapper;

  @BeforeEach
  void setUp() {
    this.mockWebServer = new MockWebServer();
    URI mockBaseUri = this.mockWebServer.url("/").uri();
    WebClient webClient = WebClient.builder().baseUrl(mockBaseUri.toString()).build();
    this.customerMapper = new CustomerMapperImpl();
    this.customerServiceImpl =
        new CustomerServiceImpl(customerRepository, authSvcRepository, customerMapper, webClient);
    this.customerRegisterDto =
        CustomerRegisterDto.builder()
            .username(username)
            .firstName(firstName)
            .lastName(lastName)
            .email(email)
            .password(password)
            .build();
    Customer customerMockResponse = new Customer();
    customerMockResponse.setCustomerUuid(customerUuid);
    customerMockResponse.setFirstName(firstName);
    customerMockResponse.setLastName(lastName);
    given(customerRepository.save(any(Customer.class))).willReturn(customerMockResponse);
  }

//  @Test
//  @DisplayName("Register a new customer")
//  void givenRegisterDto_whenCreate_thenReturnSuccessfulCustomer()
//      throws IOException, InterruptedException {
//    // given
//    InputStream inputStream =
//        classLoader.getResourceAsStream("mockresponse/accountRegisterSuccessful.json");
//    assertThat(inputStream).isNotNull();
//    mockWebServer.enqueue(
//        new MockResponse()
//            .setResponseCode(201)
//            .addHeader("Content-Type", "application/json")
//            .setBody(new String(inputStream.readAllBytes())));
//
//    inputStream.close();
//
//    // when
//    customerServiceImpl.create(customerRegisterDto);
//
//    // then
//    RecordedRequest recordedRequest = mockWebServer.takeRequest();
//    JsonContent<Object> body = jsonTester.from(recordedRequest.getBody().readUtf8());
//    assertThat(body).extractingJsonPathValue("$.username").isEqualTo(username);
//    assertThat(body).extractingJsonPathValue("$.email").isEqualTo(email);
//    assertThat(body).extractingJsonPathStringValue("$.uuid").isNotEmpty();
//  }

//  @Test
//  @DisplayName("Register a new customer but conflict at auth-service")
//  void givenConflictRegisterAccountDto_whenCreate_thenThrowWebClientResponseException()
//      throws IOException {
//    // given
//    InputStream inputStream =
//        classLoader.getResourceAsStream("mockresponse/accountRegisterReturnConflict.json");
//    assertThat(inputStream).isNotNull();
//    mockWebServer.enqueue(
//        new MockResponse()
//            .setResponseCode(409)
//            .addHeader("Content-Type", "application/json")
//            .setBody(new String(inputStream.readAllBytes())));
//
//    inputStream.close();
//
//    // when & then
//    assertThatThrownBy(() -> customerServiceImpl.create(customerRegisterDto))
//        .isInstanceOf(WebClientResponseException.class)
//        .hasMessageContaining("409 Conflict");
//  }

  @Test
  @DisplayName("Get customer details")
  @WithMockUser(username = "1a35d863-0cd9-4bc1-8cc4-f4cddca97721")
  @Disabled(value = "auth-svc not implement")
  void givenCustomerExists_whenGetCustomer_thenReturnCustomerDto() {
    // given
    Customer customer = new Customer();
    customer.setCustomerUuid(customerUuid);
    customer.setFirstName(firstName);
    customer.setLastName(lastName);
    given(customerRepository.findCustomerByCustomerUuid(customerUuid))
        .willReturn(Optional.of(customer));

    Account account = new Account();

    mockWebServer.enqueue(new MockResponse().setResponseCode(200));

    // when
    CustomerDto customerDto = customerServiceImpl.getCustomer();

    // then
    assertThat(customerDto).isNotNull();
    then(customerRepository).should().findCustomerByCustomerUuid(customerUuid);
  }

  @Test
  @DisplayName("Get customer details but customer not found")
  @WithMockUser("1a35d863-0cd9-4bc1-8cc4-f4cddca97721")
  @Disabled(value = "auth-svc not implement")
  void givenCustomerNotFound_whenGetCustomer_thenThrowNotFoundException() {
    // given
    given(customerRepository.findCustomerByCustomerUuid(customerUuid)).willReturn(Optional.empty());
    // when & then
    assertThatThrownBy(() -> customerServiceImpl.getCustomer())
        .isInstanceOf(NotFoundException.class);
  }
}
