package org.example.addresssvc.service;

import org.example.addresssvc.dto.AddressDto;
import org.example.addresssvc.dto.AddressResponseDto;
import org.example.addresssvc.dto.mapper.AddressMapper;
import org.example.addresssvc.exception.AddressConflictException;
import org.example.addresssvc.exception.AddressNotFoundException;
import org.example.addresssvc.persistence.document.Address;
import org.example.addresssvc.persistence.repository.AddressRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
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
        Mono<Address> mono =   addressRepository.save(address) ;

     return addressRepository.findAddressByName(address.getName())
             .filter(a -> a.getCustomerUuid().equals(address.getCustomerUuid()))
             .flatMap(a -> Mono.error(new AddressConflictException("Address already exists")))
             .switchIfEmpty(mono).then();
    }

    @Override
    public Mono<AddressResponseDto> getAddressByCustomerUuid(UUID customerUuid) {
        return addressRepository.findAddressByCustomerUuid(customerUuid)
                .switchIfEmpty(Mono.error(new AddressNotFoundException("Address not found")))
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
    public Mono<Void> updateAddress(UUID addressUuid, AddressDto addressDto) {

        return addressRepository.findAddressByUuid(addressUuid)
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(a  -> {
                   Boolean exists = addressRepository.existsAddressByName(addressDto.getName()).block();
                     if(Boolean.TRUE.equals(exists)) {
                          throw new AddressConflictException("Address already exists");
                     }
                    Address address = addressMapper.toAddress(addressDto);
                    address.setUuid(addressUuid);
                    address.setCustomerUuid(a.getCustomerUuid());
                    address.setLastModifiedAt(LocalDateTime.now());
                })
                .map(addressRepository::save)
                .then();




    }

    @Override
    public Mono<Void> deleteAddress(UUID customerUuid) {
        return null;
    }
}
