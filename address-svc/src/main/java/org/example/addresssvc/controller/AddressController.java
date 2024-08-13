package org.example.addresssvc.controller;

import jakarta.validation.Valid;
import org.example.addresssvc.dto.AddressDto;
import org.example.addresssvc.dto.AddressResponseDto;
import org.example.addresssvc.persistence.document.Address;
import org.example.addresssvc.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user/address")
public class AddressController {

    private final AddressService addressService;
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Void> createAddress(@RequestBody @Valid Address address) {
        return addressService.createAddress(address);
    }

    @GetMapping( path =  "/{customerUuid}",produces = "application/json")
    Mono<AddressResponseDto> getAddressByCustomerUuid(@PathVariable @Valid UUID customerUuid) {
        return addressService.getAddressByCustomerUuid(customerUuid);
    }

    @PutMapping(path = "/{addressUuid}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    Mono<Void> updateAddress(@Valid UUID addressUuid ,  @RequestBody @Valid AddressDto  addressDto) {
      return  addressService.updateAddress(addressUuid,addressDto);

    }

}
