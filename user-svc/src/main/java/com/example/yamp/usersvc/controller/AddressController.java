package com.example.yamp.usersvc.controller;

import com.example.yamp.usersvc.dto.address.AddressDto;
import com.example.yamp.usersvc.dto.address.AddressResponseDto;
import com.example.yamp.usersvc.service.AddressService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.UUID;
import javax.security.auth.login.AccountNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/address")
@SecurityRequirement(name = "oauth2")
public class AddressController {
  private final AddressService addressService;

  public AddressController(AddressService addressService) {
    this.addressService = addressService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createAddress(@RequestBody @Valid AddressDto addressDto)
      throws AccountNotFoundException {
    addressService.createAddress(addressDto);
  }

  @GetMapping
  public AddressResponseDto getAddresses() throws AccountNotFoundException {

    return addressService.getAddresses();
  }

  @PutMapping("/{addressUuid}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void updateAddress(
      @RequestBody @Valid AddressDto addressDto, @PathVariable UUID addressUuid)
      throws AccountNotFoundException {
    addressService.updateAddress(addressUuid, addressDto);
  }

  @DeleteMapping(path = "/{addressUuid}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteAddress(Authentication authentication, @PathVariable UUID addressUuid)
      throws AccountNotFoundException {
    Assert.notNull(authentication, "Authentication must not be null");
    Assert.notNull(authentication.getName(), "Authentication name must not be null");
    addressService.deleteAddress(addressUuid);
  }
}
