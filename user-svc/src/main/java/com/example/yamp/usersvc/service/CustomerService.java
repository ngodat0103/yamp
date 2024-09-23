package com.example.yamp.usersvc.service;

import com.example.yamp.usersvc.dto.customer.CustomerDto;
import com.example.yamp.usersvc.dto.customer.CustomerRegisterDto;
import javax.security.auth.login.AccountNotFoundException;

public interface CustomerService {
  void register(CustomerRegisterDto customerRegisterDto);

  CustomerDto getCustomer() throws AccountNotFoundException;
}
