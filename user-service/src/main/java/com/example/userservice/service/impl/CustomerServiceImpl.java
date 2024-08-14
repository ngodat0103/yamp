package com.example.userservice.service.impl;
import com.example.userservice.dto.customer.AccountDto;
import com.example.userservice.dto.customer.CustomerDto;
import com.example.userservice.dto.customer.CustomerRegisterDto;
import com.example.userservice.dto.mapper.AddressMapper;
import com.example.userservice.dto.mapper.CustomerMapper;
import com.example.userservice.exception.AddressNotFoundException;
import com.example.userservice.exception.CustomerNotFoundException;
import com.example.userservice.persistence.entity.Customer;
import com.example.userservice.persistence.repository.AddressRepository;
import com.example.userservice.persistence.repository.CustomerRepository;
import com.example.userservice.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import javax.security.auth.login.AccountNotFoundException;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.example.userservice.constant.AuthServiceUri.*;
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
        AccountDto accountDtoRequest = customerMapper.mapToAccountDto(customerRegisterDto);
        accountDtoRequest.setPassword(passwordEncoder.encode(accountDtoRequest.getPassword()));
        accountDtoRequest.setAccountUuid(customer.getCustomerUuid());
        logger.debug("New customerUuid {} saved but not commit, wait for auth-svc response", customer.getCustomerUuid());
        webClient.post()
                .uri(AUTH_SVC_REG_URI)
                .bodyValue(accountDtoRequest)
                .retrieve()
                .bodyToMono(AccountDto.class)
                .doOnError(getOnError(customer.getCustomerUuid()))
                .doOnSuccess(
                        accountDtoResponse -> {
                            accountDtoRequest.setPassword(null);
                            if(accountDtoResponse.equals(accountDtoRequest)){
                                logger.debug("Account created successfully: {}", accountDtoResponse.getAccountUuid());
                            }
                            else {
                                throw new RuntimeException("Inconsistent account data");
                            }
                        }
                )
                .block();
    }




    @Override
    public CustomerDto getCustomer(UUID accountUuid,String correlationId) throws AccountNotFoundException {
        Customer customer = customerRepository.findCustomerByCustomerUuid(accountUuid)
                .orElseThrow(accountNotFoundExceptionSupplier(accountUuid));
        CustomerDto customerDto = customerMapper.mapToDto(customer);
        ResponseEntity<AccountDto> accountDtoResponse = webClient.get()
                .uri(AUTH_SVC_ACC_URI)
                .headers(h -> {
                    h.add(X_ACCOUNT_UUID_HEADER, accountUuid.toString());
                    h.add(CORRELATION_ID_HEADER, correlationId);
                })
                .retrieve()
                .toEntity(AccountDto.class)
                .block();
        assert accountDtoResponse != null;
        if (accountDtoResponse.getStatusCode().is2xxSuccessful()) {
            AccountDto accountDto = accountDtoResponse.getBody();
            assert accountDto != null;
            customerDto.setAccount(accountDto);
            logger.debug("CustomerDto: {}", customerDto);
            return customerDto;
        } else {
            logger.error("Failed to get account from auth-service: {}", accountUuid);
        }
        return null;
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
