package com.example.userservice.service;

import com.example.userservice.dto.customer.CustomerDto;
import com.example.userservice.dto.customer.CustomerRegisterDto;
import jakarta.transaction.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.util.UUID;

public interface CustomerService {
    void register(CustomerRegisterDto customerRegisterDto, String correlationId);
    CustomerDto getCustomer(UUID accountUuid,String correlationId) throws AccountNotFoundException;

}
