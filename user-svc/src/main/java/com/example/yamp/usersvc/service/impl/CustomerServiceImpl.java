package com.example.yamp.usersvc.service.impl;
import com.example.yamp.usersvc.dto.customer.AccountDto;
import com.example.yamp.usersvc.dto.customer.AccountRegisterDto;
import com.example.yamp.usersvc.dto.customer.CustomerDto;
import com.example.yamp.usersvc.dto.customer.CustomerRegisterDto;
import com.example.yamp.usersvc.dto.mapper.AddressMapper;
import com.example.yamp.usersvc.dto.mapper.CustomerMapper;
import com.example.yamp.usersvc.exception.AddressNotFoundException;
import com.example.yamp.usersvc.exception.CustomerNotFoundException;
import com.example.yamp.usersvc.persistence.entity.Customer;
import com.example.yamp.usersvc.persistence.repository.AddressRepository;
import com.example.yamp.usersvc.persistence.repository.CustomerRepository;
import com.example.yamp.usersvc.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
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
import static org.slf4j.LoggerFactory.getLogger;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final Logger logger = getLogger(CustomerServiceImpl.class);
    private final  CustomerRepository  customerRepository;
    private final AddressRepository addressRepository;
    private final CustomerMapper customerMapper;
    private final AddressMapper addressMapper;
    private final WebClient webClient;
    private final PasswordEncoder passwordEncoder;




    @Transactional
    @Override
    public void register(CustomerRegisterDto customerRegisterDto, String correlationId) {
        Customer customer = customerMapper.mapToEntity(customerRegisterDto);
        customer = customerRepository.save(customer);
        AccountRegisterDto accountRegisterDtoRequest = customerMapper.maptoAccountRegisterDto(customerRegisterDto);
        accountRegisterDtoRequest.setPassword(passwordEncoder.encode(accountRegisterDtoRequest.getPassword()));
        accountRegisterDtoRequest.setAccountUuid(customer.getCustomerUuid());
        logger.debug("New customerUuid {} saved but not commit, wait for auth-svc response", customer.getCustomerUuid());
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
                                logger.debug("Account created successfully: {}", accountRegisterDtoResponse.getAccountUuid());
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
                .orElseThrow(accountNotFoundExceptionSupplier(customerUuid));
        CustomerDto customerDto = customerMapper.mapToDto(customer);
        String username = jwt.getClaimAsString("username");
        String email = jwt.getClaimAsString("email");
        AccountDto accountDto = new AccountDto(username,email);
        customerDto.setAccount(accountDto);
        return customerDto;
    }


    Supplier<CustomerNotFoundException> accountNotFoundExceptionSupplier(UUID customerUuid){
        return ()-> {
            logger.debug("Account not found for UUID: {}", customerUuid);
            return new CustomerNotFoundException(customerUuid);
        };
    }

    Supplier<AddressNotFoundException> addressNotFoundExceptionSupplier(UUID addressUuid){
        return ()-> {
            logger.debug("Address not found for UUID: {}", addressUuid);
            return new AddressNotFoundException(addressUuid);
        };
    }


    private Consumer<?super Throwable> getOnError(UUID customerUuid){
        return (error)->{
            if(error instanceof WebClientResponseException responseException){
                logger.debug("WebClientResponseException: {}", responseException.getResponseBodyAsString());
            }
            else if(error instanceof WebClientRequestException requestException){
                logger.error("WebClientRequestException: {}", requestException.getMessage());
            }
            else {
                logger.error("Exception: {}", error.getMessage());
            }
            logger.debug("Roll back customer creation for customerUUid: {}", customerUuid);
        };
    }


}
