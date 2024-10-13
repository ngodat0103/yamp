package com.example.yamp.usersvc.service.impl;

import static com.example.yamp.usersvc.Util.*;

import com.example.yamp.usersvc.cache.AuthSvcRepository;
import com.example.yamp.usersvc.dto.customer.CustomerDto;
import com.example.yamp.usersvc.dto.kafka.AccountTopicContent;
import com.example.yamp.usersvc.dto.mapper.CustomerMapper;
import com.example.yamp.usersvc.persistence.entity.Account;
import com.example.yamp.usersvc.persistence.entity.Customer;
import com.example.yamp.usersvc.persistence.repository.CustomerRepository;
import com.example.yamp.usersvc.service.CustomerService;
import jakarta.transaction.Transactional;
import java.util.UUID;
import java.util.function.Consumer;
import javax.security.auth.login.AccountNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
  private final CustomerRepository customerRepository;
  private final AuthSvcRepository authSvcRepository;
  private final CustomerMapper customerMapper;
  private final WebClient webClient;
  private static final String ACCOUNT_PATH = "/accounts";

  @Transactional
  @Override
  public void create(UUID customerUuid, AccountTopicContent accountTopicContent) {
    if (customerRepository.findCustomerByCustomerUuid(customerUuid).isPresent()) {
      log.debug("customerUuid: {} already exists", customerUuid);
      return;
    }
    Customer newCustomer = customerMapper.MapToCustomerEntity(accountTopicContent);
    newCustomer.setCustomerUuid(customerUuid);

    customerRepository.save(newCustomer);
    log.debug("newCustomer: {} saved to database", customerUuid);
  }

  @Override
  public CustomerDto getCustomer() {
    UUID customerUuid = getUuidFromAuthentication();
    Customer customer =
        customerRepository
            .findCustomerByCustomerUuid(customerUuid)
            .orElseThrow(notFoundExceptionSupplier(log, "Customer", "customerUuid", customerUuid));

    Account account = loadAccount(customerUuid);
    assert account != null;
    authSvcRepository.save(account);
    CustomerDto customerDto = customerMapper.mapToDto(customer);
    customerDto.setAccount(customerMapper.mapToDto(account));
    log.debug("Fetching successful,Account with {} saved to redis cache", customerUuid);
    return customerDto;
  }

  @Override
  public CustomerDto getCustomer(UUID uuid) throws AccountNotFoundException {
    Customer customer =
        customerRepository
            .findCustomerByCustomerUuid(uuid)
            .orElseThrow(() -> new AccountNotFoundException("Customer not found"));
    Account account = loadAccount(uuid);
    CustomerDto customerDto = customerMapper.mapToDto(customer);
    customerDto.setAccount(customerMapper.mapToDto(account));
    return customerDto;
  }

  private Consumer<? super Throwable> getOnError(UUID customerUuid) {
    return (error) -> {
      if (error instanceof WebClientResponseException responseException) {
        log.debug("WebClientResponseException: {}", responseException.getResponseBodyAsString());
      } else if (error instanceof WebClientRequestException requestException) {
        log.debug("WebClientRequestException: {}", requestException.getMessage());
      } else {
        log.debug("Exception: {}", error.getMessage());
      }
      log.debug("Roll back customer creation for customerUUid: {}", customerUuid);
    };
  }

  private Account loadAccount(UUID customerUuid) {
    Account account = checkRedisCache(customerUuid);
    if (account != null) {
      if (log.isDebugEnabled()) {
        log.debug("Cache hit,get account with {} from cache", customerUuid);
      }
      return account;
    }
    log.debug("Cache miss,fetching account with {} from auth-svc", customerUuid);
    account =
        webClient
            .get()
            .uri(ACCOUNT_PATH + "/" + customerUuid)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Account.class)
            .doOnError(getOnError(customerUuid))
            .block();
    assert account != null;
    if (log.isDebugEnabled()) {
      log.debug("Save Account {} to cache", customerUuid);
    }
    authSvcRepository.save(account);
    return account;
  }

  private Account checkRedisCache(UUID customerUuid) {
    return authSvcRepository.findById(customerUuid).orElse(null);
  }
}
