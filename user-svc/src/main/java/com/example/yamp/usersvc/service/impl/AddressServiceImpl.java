package com.example.yamp.usersvc.service.impl;
import com.example.yamp.usersvc.dto.address.AddressDto;
import com.example.yamp.usersvc.dto.address.AddressResponseDto;
import com.example.yamp.usersvc.dto.mapper.AddressMapper;
import com.example.yamp.usersvc.exception.NotFoundException;
import com.example.yamp.usersvc.persistence.entity.Address;
import com.example.yamp.usersvc.persistence.entity.Customer;
import com.example.yamp.usersvc.persistence.repository.AddressRepository;
import com.example.yamp.usersvc.persistence.repository.CustomerRepository;
import com.example.yamp.usersvc.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

import static com.example.yamp.usersvc.exception.Util.addressNotFoundExceptionSupplier;
import static com.example.yamp.usersvc.exception.Util.customerNotFoundExceptionSupplier;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository ;
    private final CustomerRepository customerRepository;
    private final AddressMapper addressMapper;

    @Override
    public void createAddress(UUID customerUuid, AddressDto addressDto) {
        Customer customer = customerRepository.findCustomerByCustomerUuid(customerUuid)
                .orElseThrow(customerNotFoundExceptionSupplier(log,customerUuid));

        addressRepository.findAddressByCustomerUuidAndName(customerUuid,addressDto.getName())
                .ifPresent(address -> {
                    log.debug("Address name conflict for customer UUID: {} and address name: {}", customerUuid, addressDto.getName());
                    throw new NotFoundException(address.getName());
                });
        Address address = addressMapper.mapToEntity(addressDto);
        address.setCustomerUuid(customerUuid);
        customer.setLastModifiedDate(LocalDateTime.now());
        customerRepository.save(customer);
        addressRepository.save(address);
    }

    @Override
    public AddressResponseDto getAddresses(UUID customerUuid) {
        customerRepository.findCustomerByCustomerUuid(customerUuid)
                .orElseThrow(customerNotFoundExceptionSupplier(log,customerUuid));

        Set<Address> addresses = addressRepository.findAddressByCustomerUuid(customerUuid);
        if(addresses.isEmpty()){
            log.debug("No address found for customer UUID: {}", customerUuid);

        }
        Set<AddressDto> addressDtos = addressMapper.mapToDtos(addresses);
        return AddressResponseDto.builder()
                .customerUuid(customerUuid)
                .addresses(addressDtos)
                .currentElements(addressDtos.size())
                .totalElements(addresses.size())
                .currentPage(1)
                .totalPages(1)
                .build();

    }

    @Override
    public void updateAddress(UUID customerUuid,UUID addressUuid, AddressDto addressDto) {
        Customer customer = customerRepository.findCustomerByCustomerUuid(customerUuid)
                .orElseThrow(customerNotFoundExceptionSupplier(log,customerUuid));
        addressRepository.findAddressByCustomerUuidAndName(customerUuid, addressDto.getName())
                .orElseThrow(addressNotFoundExceptionSupplier(log,addressUuid));
        Address  address = addressMapper.mapToEntity(addressDto);




        address.setCustomerUuid(customerUuid);
        address.setUuid(addressUuid);
        addressRepository.save(address);
        customer.setLastModifiedDate(LocalDateTime.now());
        customerRepository.save(customer);
    }

    @Override
    public void deleteAddress(UUID customerUuid, UUID addressUuid) {
        customerRepository.findCustomerByCustomerUuid(customerUuid)
                .orElseThrow(customerNotFoundExceptionSupplier(log,customerUuid));
        addressRepository.deleteByUuid(addressUuid);

    }





}
