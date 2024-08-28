package com.example.yamp.usersvc.service.impl;
import com.example.yamp.usersvc.dto.address.AddressDto;
import com.example.yamp.usersvc.dto.address.AddressResponseDto;
import com.example.yamp.usersvc.dto.mapper.AddressMapper;
import com.example.yamp.usersvc.exception.ConflictException;
import com.example.yamp.usersvc.exception.NotFoundException;
import com.example.yamp.usersvc.persistence.entity.Address;
import com.example.yamp.usersvc.persistence.entity.Customer;
import com.example.yamp.usersvc.persistence.repository.AddressRepository;
import com.example.yamp.usersvc.persistence.repository.CustomerRepository;
import com.example.yamp.usersvc.service.AddressService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

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
    @Transactional
    public void createAddress(AddressDto addressDto) {
        UUID customerUuid = getCustomerUuidFromAuthentication();
        Customer customer = customerRepository.findCustomerByCustomerUuid(customerUuid)
                .orElseThrow(customerNotFoundExceptionSupplier(log,customerUuid));

        addressRepository.findAddressByCustomerUuidAndName(customerUuid,addressDto.getName())
                .ifPresent(address -> {
                    log.debug("Address name conflict for customer UUID: {} and address name: {}", customerUuid, addressDto.getName());
                    throw new ConflictException("Address name conflict");
                });
        Address address = addressMapper.mapToEntity(addressDto);
        address.setCustomerUuid(customerUuid);
        customer.setLastModifiedDate(LocalDateTime.now());
        customerRepository.save(customer);
        addressRepository.save(address);
    }

    @Override
    public AddressResponseDto getAddresses() {
        UUID customerUuid = getCustomerUuidFromAuthentication();
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
    @Transactional
    public void updateAddress(UUID addressUuid, AddressDto addressDto) {

        UUID customerUuid = getCustomerUuidFromAuthentication();
        Customer customer = customerRepository.findCustomerByCustomerUuid(customerUuid)
                .orElseThrow(customerNotFoundExceptionSupplier(log,customerUuid));
        addressRepository.findById(addressUuid)
                .orElseThrow(addressNotFoundExceptionSupplier(log,addressUuid));
        Address  address = addressMapper.mapToEntity(addressDto);

        address.setCustomerUuid(customerUuid);
        address.setUuid(addressUuid);
        addressRepository.save(address);
        customer.setLastModifiedDate(LocalDateTime.now());
        customerRepository.save(customer);
    }


    @Transactional
    @Override
    public void deleteAddress(UUID addressUuid) {
        UUID customerUuid = getCustomerUuidFromAuthentication();
        customerRepository.findCustomerByCustomerUuid(customerUuid)
                .orElseThrow(customerNotFoundExceptionSupplier(log,customerUuid));

        if(!addressRepository.existsById(addressUuid)){
            log.debug("Address not found for customer UUID: {} and address UUID: {}", customerUuid, addressUuid);
            throw new NotFoundException("Address not found");
        }
        addressRepository.deleteByUuid(addressUuid);
    }

    private UUID getCustomerUuidFromAuthentication(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Assert.notNull(authentication,"Authentication is required");
        Assert.notNull(authentication.getName(),"Customer UUID is required");
        return UUID.fromString(authentication.getName());
    }
}
