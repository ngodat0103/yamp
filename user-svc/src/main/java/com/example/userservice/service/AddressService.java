package com.example.userservice.service;

import com.example.userservice.dto.address.AddressDto;
import com.example.userservice.dto.address.AddressResponseDto;

import javax.security.auth.login.AccountNotFoundException;
import java.util.UUID;
public interface AddressService {
    void createAddress(UUID customerUuid, AddressDto addressDto) throws AccountNotFoundException;
    AddressResponseDto getAddresses(UUID customerUuid) throws AccountNotFoundException;
    void updateAddress(UUID customerUuid,UUID addresssUuid ,AddressDto addressDto) throws AccountNotFoundException;
    void deleteAddress(UUID customerUuid, UUID addressUuid) throws AccountNotFoundException;
}
