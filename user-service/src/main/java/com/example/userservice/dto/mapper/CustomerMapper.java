package com.example.userservice.dto.mapper;

import com.example.userservice.dto.customer.AccountDto;
import com.example.userservice.dto.customer.CustomerDto;
import com.example.userservice.dto.customer.CustomerRegisterDto;
import com.example.userservice.persistence.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    @Mapping(target = "accountUuid",ignore = true)
    @Mapping(target = "account",ignore = true)
    CustomerDto mapToDto(Customer customer);

    Customer mapToEntity(CustomerRegisterDto customerRegisterDto);

    @Mapping(target = "accountUuid",source = "customerUuid")
    AccountDto mapToAccountDto(Customer customer);
}
