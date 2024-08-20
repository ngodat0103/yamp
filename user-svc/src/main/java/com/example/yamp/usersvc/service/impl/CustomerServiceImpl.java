package com.example.yamp.usersvc.service.impl;
import com.example.yamp.usersvc.dto.customer.AccountDto;
import com.example.yamp.usersvc.dto.customer.AccountRegisterDto;
import com.example.yamp.usersvc.dto.customer.CustomerDto;
import com.example.yamp.usersvc.dto.customer.CustomerRegisterDto;
import com.example.yamp.usersvc.dto.mapper.AddressMapper;
import com.example.yamp.usersvc.dto.mapper.CustomerMapper;
import com.example.yamp.usersvc.exception.NotFoundException;
import com.example.yamp.usersvc.persistence.entity.Customer;
import com.example.yamp.usersvc.persistence.repository.AddressRepository;
import com.example.yamp.usersvc.persistence.repository.CustomerRepository;
import com.example.yamp.usersvc.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.example.yamp.usersvc.constant.AuthServiceUri.*;
import static com.example.yamp.usersvc.exception.Util.customerNotFoundExceptionSupplier;
import static org.slf4j.LoggerFactory.getLogger;


@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final  CustomerRepository  customerRepository;
    private final AddressRepository addressRepository;
    private final CustomerMapper customerMapper;
    private final AddressMapper addressMapper;
    private final WebClient webClient;
    private final PasswordEncoder passwordEncoder;




    @Transactional
    @Override
    public void register(CustomerRegisterDto customerRegisterDto) {
        Customer customer = customerMapper.mapToEntity(customerRegisterDto);
        customer = customerRepository.save(customer);
        AccountRegisterDto accountRegisterDtoRequest = customerMapper.maptoAccountRegisterDto(customerRegisterDto);
        accountRegisterDtoRequest.setPassword(passwordEncoder.encode(accountRegisterDtoRequest.getPassword()));
        accountRegisterDtoRequest.setAccountUuid(customer.getCustomerUuid());
        log.debug("New customerUuid {} saved but not commit, wait for auth-svc response", customer.getCustomerUuid());
        webClient.post()
                .uri(AUTH_SVC_REG_URI)
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
                                throw new RuntimeException("Inconsistent account data");
                            }
                        }
                )
                .block();
    }


    @Override
    public CustomerDto getCustomer(Jwt jwt, String correlationId) {
        UUID customerUuid = UUID.fromString(jwt.getClaimAsString("X-Account-Uuid"));
        Customer customer = customerRepository.findById(customerUuid)
                .orElseThrow(customerNotFoundExceptionSupplier(log,customerUuid));
        CustomerDto customerDto = customerMapper.mapToDto(customer);
        String username = jwt.getClaimAsString("username");
        String email = jwt.getClaimAsString("email");
        AccountDto accountDto = new AccountDto(username,email);
        customerDto.setAccount(accountDto);
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


}
