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
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import javax.security.auth.login.AccountNotFoundException;
import java.util.UUID;
import java.util.function.Supplier;

import static com.example.userservice.constant.AuthServiceUri.*;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(CustomerServiceImpl.class);
    private final  CustomerRepository  customerRepository;
    private final AddressRepository addressRepository;
    private final CustomerMapper customerMapper;
    private final AddressMapper addressMapper;
    private final WebClient webClient;

    @Override
    public void register(CustomerRegisterDto customerRegisterDto, String correlationId) {
        Customer customer = customerMapper.mapToEntity(customerRegisterDto);
        customer = customerRepository.save(customer);
        AccountDto accountDtoRequest = customerMapper.mapToAccountDto(customer);
        logger.debug("Customer saved but not commit: {}", customer);
        AccountDto accountDtoResponse = webClient
                .post()
                .uri(AUTH_SVC_REG_URI)
                .bodyValue(accountDtoRequest)
                .retrieve()
                .bodyToMono(AccountDto.class)
                .block();

        if(accountDtoRequest.equals(accountDtoResponse)) {
            logger.debug("Account created successfully: {}", accountDtoResponse);
        }
        else {
            logger.error( "Failed to create account: {}", accountDtoRequest);
            logger.error("roll back customer creation: {}", customer);
            throw new RuntimeException("Failed to create account");
        }

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


}
