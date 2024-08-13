package org.example.addresssvc.service;

import org.example.addresssvc.dto.AddressDto;
import org.example.addresssvc.dto.AddressResponseDto;
import org.example.addresssvc.persistence.document.Address;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AddressService {
    Mono<Void> createAddress(Address address);

    Mono<AddressResponseDto> getAddressByCustomerUuid(UUID customerUuid);

    Mono<Void> updateAddress(UUID addressUuid, AddressDto address);

    Mono<Void> deleteAddress(UUID customerUuid);
}
