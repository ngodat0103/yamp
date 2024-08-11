package org.example.addresssvc.service;

import org.example.addresssvc.dto.AddressResponseDto;
import org.example.addresssvc.dto.mapper.AddressMapper;
import org.example.addresssvc.persistence.document.Address;
import org.example.addresssvc.persistence.repository.AddressRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService{

    private final AddressRepository addressRepository;

    private final AddressMapper addressMapper;

    public AddressServiceImpl(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }


    @Override
    public Mono<Void> createAddress(Address address) {
        address.setUuid(UUID.randomUUID());
        return addressRepository.save(address).then();

    }

    @Override
    public Mono<AddressResponseDto> getAddressByCustomerUuid(UUID customerUuid) {
        return addressRepository.findAddressByCustomerUuid(customerUuid)
                .map(addressMapper::toAddressDto)
                .collect(Collectors.toSet())
                .map(addressDtos ->
                    AddressResponseDto.builder()
                            .customerUuid(customerUuid)
                            .elements(addressDtos.size())
                            .data(addressDtos)
                            .currentPage(1)
                            .totalPages(1)
                            .build()
                );


    }

    @Override
    public Mono<Address> updateAddress(Address address) {
        return null;
    }

    @Override
    public Mono<Void> deleteAddress(UUID customerUuid) {
        return null;
    }
}
