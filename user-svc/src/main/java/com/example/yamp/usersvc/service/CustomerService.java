package com.example.yamp.usersvc.service;

import com.example.yamp.usersvc.dto.customer.CustomerDto;
import com.example.yamp.usersvc.dto.customer.CustomerRegisterDto;
import org.springframework.security.oauth2.jwt.Jwt;

import javax.security.auth.login.AccountNotFoundException;

public interface CustomerService {
    void register(CustomerRegisterDto customerRegisterDto, String correlationId);
    CustomerDto getCustomer(Jwt jwt, String correlationId) throws AccountNotFoundException;

}
