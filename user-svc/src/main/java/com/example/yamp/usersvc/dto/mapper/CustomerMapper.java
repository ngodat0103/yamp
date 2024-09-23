package com.example.yamp.usersvc.dto.mapper;

import com.example.yamp.usersvc.dto.customer.AccountDto;
import com.example.yamp.usersvc.dto.customer.AccountRegisterDto;
import com.example.yamp.usersvc.dto.customer.CustomerDto;
import com.example.yamp.usersvc.dto.customer.CustomerRegisterDto;
import com.example.yamp.usersvc.persistence.entity.Account;
import com.example.yamp.usersvc.persistence.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
  @Mapping(target = "accountUuid", ignore = true)
  @Mapping(target = "account", ignore = true)
  CustomerDto mapToDto(Customer customer);

  AccountDto mapToDto(Account customer);

  Customer mapToEntity(CustomerRegisterDto customerRegisterDto);

  AccountRegisterDto maptoAccountRegisterDto(CustomerRegisterDto customerRegisterDto);
}
