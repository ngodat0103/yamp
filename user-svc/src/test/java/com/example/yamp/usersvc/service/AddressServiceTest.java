package com.example.yamp.usersvc.service;
import com.example.yamp.usersvc.dto.address.AddressDto;
import com.example.yamp.usersvc.dto.address.AddressResponseDto;
import com.example.yamp.usersvc.dto.mapper.AddressMapper;
import com.example.yamp.usersvc.dto.mapper.AddressMapperImpl;
import com.example.yamp.usersvc.exception.ConflictException;
import com.example.yamp.usersvc.exception.NotFoundException;
import com.example.yamp.usersvc.persistence.entity.Address;
import com.example.yamp.usersvc.persistence.entity.Customer;
import com.example.yamp.usersvc.persistence.repository.AddressRepository;
import com.example.yamp.usersvc.persistence.repository.CustomerRepository;
import com.example.yamp.usersvc.service.impl.AddressServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("unit-test")
class AddressServiceTest {
    private AddressRepository addressRepository;
    private CustomerRepository customerRepository;
    private final AddressMapper addressMapper = new AddressMapperImpl();
    private static final UUID customerUuid = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    private static final String customerUuidString = "123e4567-e89b-12d3-a456-426614174000";


    AddressDto addressDto =AddressDto.builder()
            .name("Home")
            .cityName("Tphcm")
            .phoneNumber("0123456789")
            .province("Tphcm")
            .street("101 some street")
            .ward("Some ward")
            .district("Some district")
            .addressType("Home")
            .build();

    Address address = addressMapper.mapToEntity(addressDto);
    private AddressServiceImpl addressService;
    private Customer customer;

    @BeforeEach
    void setUp() {
        this.addressRepository = mock(AddressRepository.class);
        this.customerRepository = mock(CustomerRepository.class);
        this.addressService = new AddressServiceImpl(addressRepository, customerRepository, addressMapper);
        Address address = new Address();
        address.setUuid(UUID.randomUUID());
        address.setName("Home");

        Customer customer = new Customer();
        customer.setCustomerUuid(UUID.fromString(customerUuidString));
        customer.setFirstName("John");
        customer.setLastName("Doe");
        this.customer = customer;



    }

    @Test
    @DisplayName("Given valid address, when createAddress, then address is created")
    @WithMockUser(username = customerUuidString,roles = "CUSTOMER")
    void givenValidAddress_whenCreateAddress_thenAddressIsCreated() {
        given(customerRepository.findCustomerByCustomerUuid(UUID.fromString(customerUuidString))).willReturn(Optional.of(customer));
        addressService.createAddress(addressDto);
    }

    @Test
    @DisplayName("Create a address but have conflict Name")
    @WithMockUser(username = customerUuidString,roles = "CUSTOMER")
    void givenExistingAddressName_whenCreateAddress_thenThrowConflictException() {
        given(customerRepository.findCustomerByCustomerUuid(customerUuid)).willReturn(Optional.of(customer));
        given(addressRepository.findAddressByCustomerUuidAndName(customerUuid, addressDto.getName())).willReturn(Optional.of(address));
        thenThrownBy(() -> addressService.createAddress(addressDto))
                .isInstanceOf(ConflictException.class)
                .hasMessageContaining("Address name conflict");
    }

    @Test
    @DisplayName("Given valid customer, when getAddresses, then return addresses")
    @WithMockUser(username = customerUuidString,roles = "CUSTOMER")
    void givenValidCustomer_whenGetAddresses_thenReturnAddresses() {
        Set<Address> addresses = Set.of(address);

        given(customerRepository.findCustomerByCustomerUuid(customerUuid)).willReturn(Optional.of(customer));
        given(addressRepository.findAddressByCustomerUuid(customerUuid)).willReturn(addresses);


        AddressResponseDto response = addressService.getAddresses();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(customerUuid, response.getCustomerUuid());
        Assertions.assertEquals(addresses.size(), response.getCurrentElements());
        Assertions.assertEquals(addresses.size(), response.getTotalElements());
        Assertions.assertEquals(1, response.getTotalPages());
        Assertions.assertNotNull(response.getAddresses());
        Assertions.assertEquals(addresses.size(), response.getAddresses().size());
        Assertions.assertEquals(addressDto, response.getAddresses().iterator().next());


    }

    @Test
    @DisplayName("Given non-existing customer, when getAddresses, then throw NotFoundException")
    @WithMockUser(username = customerUuidString,roles = "CUSTOMER")
    void givenNonExistingCustomer_whenGetAddresses_thenThrowNotFoundException() {

        given(customerRepository.findCustomerByCustomerUuid(customerUuid)).willReturn(Optional.empty());

        thenThrownBy(() -> addressService.getAddresses())
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Customer not found for UUID");
    }

    @Test
    @DisplayName("Given valid address, when updateAddress, then address is updated")
    @WithMockUser(username = customerUuidString,roles = "CUSTOMER")
    void givenValidAddress_whenUpdateAddress_thenAddressIsUpdated() {

        given(customerRepository.findCustomerByCustomerUuid(customerUuid)).willReturn(Optional.of(customer));
        given(addressRepository.findById(address.getUuid())).willReturn(Optional.of(address));
        addressService.updateAddress(address.getUuid(),addressDto);
    }

    @Test
    @DisplayName("Given non-existing address, when updateAddress, then throw NotFoundException")
    @WithMockUser(username = customerUuidString,roles = "CUSTOMER")

    void givenNonExistingAddress_whenUpdateAddress_thenThrowNotFoundException() {


        given(customerRepository.findCustomerByCustomerUuid(customerUuid)).willReturn(Optional.of(customer));
        given(addressRepository.findById(address.getUuid())).willReturn( Optional.empty());

        thenThrownBy(() -> addressService.updateAddress( address.getUuid(), addressDto))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Address not found");
    }

    @Test
    @DisplayName("Given valid address, when deleteAddress, then address is deleted")
    @WithMockUser(username = customerUuidString,roles = "CUSTOMER")

    void givenValidAddress_whenDeleteAddress_thenAddressIsDeleted() {
        given(customerRepository.findCustomerByCustomerUuid(customerUuid)).willReturn(Optional.of(customer));
        given(addressRepository.existsById(address.getUuid())).willReturn(true);
        addressService.deleteAddress(address.getUuid());
    }

    @Test
    @DisplayName("Given non-existing address, when deleteAddress, then throw NotFoundException")
    @WithMockUser(username = customerUuidString,roles = "CUSTOMER")

    void givenNonExistingAddress_whenDeleteAddress_thenThrowNotFoundException() {

        given(customerRepository.findCustomerByCustomerUuid(customerUuid)).willReturn(Optional.of(customer));
        given(addressRepository.existsById(address.getUuid())).willReturn( false);

        thenThrownBy(() -> addressService.deleteAddress(address.getUuid()))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Address not found");
    }
}