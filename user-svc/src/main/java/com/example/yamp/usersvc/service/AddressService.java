package com.example.yamp.usersvc.service;

import com.example.yamp.usersvc.dto.address.AddressDto;
import com.example.yamp.usersvc.dto.address.AddressResponseDto;
import java.util.UUID;
import javax.security.auth.login.AccountNotFoundException;

public interface AddressService {
  void createAddress(AddressDto addressDto) throws AccountNotFoundException;

  AddressResponseDto getAddresses() throws AccountNotFoundException;

  void updateAddress(UUID addresssUuid, AddressDto addressDto) throws AccountNotFoundException;

  void deleteAddress(UUID addressUuid) throws AccountNotFoundException;
}
