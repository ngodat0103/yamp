package com.example.userservice.service.impl;
import com.example.userservice.dto.address.AddressDto;
import com.example.userservice.dto.address.AddressResponseDto;
import com.example.userservice.dto.mapper.AddressMapper;
import com.example.userservice.exception.AddressNameConflictException;
import com.example.userservice.exception.AddressNotFoundException;
import com.example.userservice.exception.CustomerNotFoundException;
import com.example.userservice.persistence.entity.Address;
import com.example.userservice.persistence.entity.Customer;
import com.example.userservice.persistence.repository.AddressRepository;
import com.example.userservice.persistence.repository.CustomerRepository;
import com.example.userservice.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository ;
    private final CustomerRepository customerRepository;
    private final AddressMapper addressMapper;
    private static final Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

    @Override
    public void createAddress(UUID customerUuid, AddressDto addressDto) {
        Customer customer = customerRepository.findCustomerByCustomerUuid(customerUuid)
                .orElseThrow(customerNotFoundExceptionSupplier(customerUuid));

        addressRepository.findAddressByCustomerUuidAndName(customerUuid,addressDto.getName())
                .ifPresent(address -> {
                    logger.debug("Address name conflict for customer UUID: {} and address name: {}", customerUuid, addressDto.getName());
                    throw new AddressNameConflictException(addressDto.getName());
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
                .orElseThrow(customerNotFoundExceptionSupplier(customerUuid));

        Set<Address> addresses = addressRepository.findAddressByCustomerUuid(customerUuid);
        if(addresses.isEmpty()){
            logger.debug("No address found for customer UUID: {}", customerUuid);

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
                .orElseThrow(customerNotFoundExceptionSupplier(customerUuid));
        addressRepository.findAddressByCustomerUuidAndName(customerUuid, addressDto.getName())
                .orElseThrow(addressNotFoundExceptionSupplier(addressUuid));
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
                .orElseThrow(customerNotFoundExceptionSupplier(customerUuid));
        addressRepository.deleteByUuid(addressUuid);

    }


    Supplier<CustomerNotFoundException> customerNotFoundExceptionSupplier(UUID customerUuid){
        return ()-> {
            logger.debug("Account not found for UUID: {}", customerUuid);
            return new CustomerNotFoundException(customerUuid);
        };
    }

    Supplier<AddressNotFoundException> addressNotFoundExceptionSupplier(UUID addressUuid){
        return ()-> {
            logger.debug("Address not found for UUID: {}", addressUuid);
            return new AddressNotFoundException(addressUuid);
        };
    }




}
