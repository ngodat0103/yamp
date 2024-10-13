package com.example.yamp.usersvc.dto.mapper;

import com.example.yamp.usersvc.dto.customer.AccountDto;
import com.example.yamp.usersvc.dto.customer.CustomerDto;
import com.example.yamp.usersvc.dto.kafka.AccountTopicContent;
import com.example.yamp.usersvc.persistence.entity.Account;
import com.example.yamp.usersvc.persistence.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {

  CustomerDto mapToCustomerDto(Customer customer);

  AccountDto mapToAccountDto(Account account);

  Customer MapToCustomerEntity(AccountTopicContent accountTopicContent);
}
