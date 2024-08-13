package com.example.userservice.service;

import com.example.userservice.dto.customer.CustomerDto;
import com.example.userservice.dto.customer.RegisterDto;

import javax.security.auth.login.AccountNotFoundException;
import java.util.UUID;

public interface CustomerService {
    void register(RegisterDto registerDto,String correlationId);
    CustomerDto getCustomer(UUID accountUuid,String correlationId) throws AccountNotFoundException;

}
