package com.example.userservice.service;

import com.example.userservice.dto.AddressDto;
import com.example.userservice.dto.CustomerDto;
import com.example.userservice.dto.RegisterDto;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.UUID;

public interface CustomerService {
    void register(RegisterDto registerDto);
    CustomerDto getCustomer(UUID accountUuid) throws AccountNotFoundException;
    CustomerDto updateCustomer(UUID accountUuid, CustomerDto customerDto);
    void deleteCustomer(UUID accountUuid);
    List<CustomerDto> getCustomers();
    List<AddressDto> getAddresses(UUID accountUuid);
    AddressDto getAddress(UUID accountUuid, UUID addressUuid);
    AddressDto addAddress(UUID accountUuid, AddressDto addressDto);
    AddressDto updateAddress(UUID accountUuid, UUID addressUuid, AddressDto addressDto);
    void deleteAddress(UUID accountUuid, UUID addressUuid);
}
