package com.example.yamp.usersvc.service;

import com.example.yamp.usersvc.dto.customer.CustomerDto;
import com.example.yamp.usersvc.dto.customer.CustomerRegisterDto;
import com.example.yamp.usersvc.dto.kafka.AccountTopicContent;

import javax.security.auth.login.AccountNotFoundException;
import java.util.UUID;

public interface CustomerService {
  void create(UUID customerUuid,AccountTopicContent accountTopicContent);

  CustomerDto getCustomer() throws AccountNotFoundException;
  CustomerDto getCustomer(UUID uuid) throws AccountNotFoundException;
}
