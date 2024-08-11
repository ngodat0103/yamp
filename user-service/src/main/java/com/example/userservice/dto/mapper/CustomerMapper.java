package com.example.userservice.dto.mapper;

import com.example.userservice.dto.CustomerDto;
import com.example.userservice.persistence.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    @Mapping(target = "accountUuid",ignore = true)
    @Mapping(target = "account",ignore = true)
    CustomerDto mapToDto(Customer customer);
}
