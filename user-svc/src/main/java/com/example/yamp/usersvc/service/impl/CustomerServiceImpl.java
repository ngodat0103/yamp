package com.example.yamp.usersvc.service.impl;
import com.example.yamp.usersvc.dto.customer.AccountRegisterDto;
import com.example.yamp.usersvc.dto.customer.CustomerDto;
import com.example.yamp.usersvc.dto.customer.CustomerRegisterDto;
import com.example.yamp.usersvc.dto.mapper.CustomerMapper;
import com.example.yamp.usersvc.persistence.entity.Account;
import com.example.yamp.usersvc.persistence.entity.Customer;
import com.example.yamp.usersvc.cache.AuthSvcRepository;
import com.example.yamp.usersvc.persistence.repository.CustomerRepository;
import com.example.yamp.usersvc.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.UUID;
import java.util.function.Consumer;

import static com.example.yamp.usersvc.Util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final  CustomerRepository  customerRepository;
    private final AuthSvcRepository authSvcRepository;
    private final CustomerMapper customerMapper;
    private final WebClient webClient;
    private final ObjectMapper objectMapper =new ObjectMapper();
    private final static String ACCOUNT_PATH = "/accounts";
    private final static String DEFAULT_ROLE = "CUSTOMER";



    @Transactional
    @Override
    public void register(CustomerRegisterDto customerRegisterDto) {
        Customer customer = customerMapper.mapToEntity(customerRegisterDto);

        customer = customerRepository.save(customer);
        AccountRegisterDto accountRegisterDtoRequest = customerMapper.maptoAccountRegisterDto(customerRegisterDto);
        accountRegisterDtoRequest.setPassword(customerRegisterDto.getPassword());
        accountRegisterDtoRequest.setAccountUuid(customer.getCustomerUuid());
        accountRegisterDtoRequest.setRoleName(DEFAULT_ROLE);
        log.debug("New customerUuid {} saved but not commit, wait for auth-svc response", customer.getCustomerUuid());
        webClient.post()
                .uri(ACCOUNT_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(accountRegisterDtoRequest)
                .retrieve()
                .bodyToMono(AccountRegisterDto.class)
                .doOnError(getOnError(customer.getCustomerUuid()))
                .doOnSuccess(
                        accountRegisterDtoResponse -> {
                            accountRegisterDtoRequest.setPassword(null);
                            if(accountRegisterDtoResponse.equals(accountRegisterDtoRequest)){
                                log.debug("Account created successfully: {}", accountRegisterDtoResponse.getAccountUuid());
                            }
                            else {
                                throw new RuntimeException("Inconsistent account data,code bug " + accountRegisterDtoResponse +"!=" + accountRegisterDtoRequest);
                            }
                        }
                )
                .block();
    }


    @Override
    public CustomerDto getCustomer() {
        UUID customerUuid = getUuidFromAuthentication();
        Customer customer = customerRepository.findCustomerByCustomerUuid(customerUuid)
                .orElseThrow(notFoundExceptionSupplier(log, "Customer", "customerUuid", customerUuid));

        Account account = checkRedisCache(customerUuid);
        if(account !=null){
            log.debug("Cache hit,get account with {} from redis cache", customerUuid);
            CustomerDto customerDto = customerMapper.mapToDto(customer);
            customerDto.setAccount(customerMapper.mapToDto(account));
            return customerDto;
        }


        log.debug("Cache miss, get account with {} by invoking auth-svc endpoint", customerUuid);
        account = webClient.get()
                .uri(ACCOUNT_PATH + "/" + customerUuid)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Account.class)
                .doOnError(getOnError(customerUuid))
                .block();

        assert account != null;
        authSvcRepository.save(account);
        CustomerDto customerDto = customerMapper.mapToDto(customer);
        customerDto.setAccount(customerMapper.mapToDto(account));
        log.debug("Fetching successful,Account with {} saved to redis cache", customerUuid);
        return customerDto;
    }



    private Consumer<?super Throwable> getOnError(UUID customerUuid){
        return (error)->{
            if(error instanceof WebClientResponseException responseException){
                log.debug("WebClientResponseException: {}", responseException.getResponseBodyAsString());
            }
            else if(error instanceof WebClientRequestException requestException){
                log.debug("WebClientRequestException: {}", requestException.getMessage());
            }
            else {
                log.debug("Exception: {}", error.getMessage());
            }
            log.debug("Roll back customer creation for customerUUid: {}", customerUuid);
        };
    }


    private Account checkRedisCache(UUID customerUuid){
        return authSvcRepository.findById(customerUuid).orElse(null);
    }
}
