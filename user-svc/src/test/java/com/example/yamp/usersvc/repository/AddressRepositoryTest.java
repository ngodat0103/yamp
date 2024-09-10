package com.example.yamp.usersvc.repository;

import com.example.yamp.usersvc.persistence.entity.Address;
import com.example.yamp.usersvc.persistence.entity.Customer;
import com.example.yamp.usersvc.persistence.repository.AddressRepository;
import com.example.yamp.usersvc.persistence.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.BDDAssertions.then;

@DataJpaTest
@ActiveProfiles("unit-test")
@Transactional
class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CustomerRepository customerRepository;

    private Address address;

    @BeforeEach
    public void setUp(){
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        Customer savedCustomer = customerRepository.save(customer);

        Address address = new Address();
        address.setCustomerUuid(savedCustomer.getCustomerUuid());
        address.setName("Home");
        address.setCityName("Ho Chi Minh");
        address.setPhoneNumber("0123456789");
        address.setProvince("Ho Chi Minh");
        address.setStreet("101 Duong abc");
        address.setWard("Some ward");
        address.setDistrict("Some District");
        address.setAddressType("Home");
        this.address = address;
    }


    @Test
    @DisplayName("Create Address")
    void givenAddress_whenCreateAddress_thenReturnSavedAddress(){
        Address savedAddress = addressRepository.save(address);
        then(savedAddress.getUuid()).isNotNull();
        then(savedAddress.getCustomerUuid()).isEqualTo(address.getCustomerUuid());
        then(savedAddress.getName()).isEqualTo(address.getName());
        then(savedAddress.getCityName()).isEqualTo(address.getCityName());
        then(savedAddress.getPhoneNumber()).isEqualTo(address.getPhoneNumber());
        then(savedAddress.getProvince()).isEqualTo(address.getProvince());
        then(savedAddress.getStreet()).isEqualTo(address.getStreet());
        then(savedAddress.getWard()).isEqualTo(address.getWard());
        then(savedAddress.getDistrict()).isEqualTo(address.getDistrict());
        then(savedAddress.getAddressType()).isEqualTo(address.getAddressType());

    }

    @Test
    @DisplayName("Given customer UUID, when findAddressByCustomerUuid, then return addresses")
    void givenCustomerUuid_whenFindAddressByCustomerUuid_thenReturnAddresses() {
        // Given
        addressRepository.save(address);
        // When
        Set<Address> addresses = addressRepository.findAddressByCustomerUuid(address.getCustomerUuid());

        // Then
        then(addresses).isNotEmpty();
        then(addresses.iterator().next().getCustomerUuid()).isEqualTo(address.getCustomerUuid());
    }

    @Test
    @DisplayName("Given customer UUID, when findAddressByCustomerUuid, then return empty set if no addresses found")
    void givenCustomerUuid_whenFindAddressByCustomerUuid_thenReturnEmptySetIfNoAddressesFound() {
        // Given
        UUID customerUuid = UUID.randomUUID();

        // When
        Set<Address> addresses = addressRepository.findAddressByCustomerUuid(customerUuid);

        // Then
        then(addresses).isEmpty();
    }

    @Test
    @DisplayName("Given customer UUID and name, when findAddressByCustomerUuidAndName, then return address")
    void givenCustomerUuidAndName_whenFindAddressByCustomerUuidAndName_thenReturnAddress() {
        // Given
        addressRepository.save(address);

        // When
        Optional<Address> foundAddress = addressRepository.findAddressByCustomerUuidAndName(address.getCustomerUuid(), address.getName());

        // Then
        then(foundAddress).isPresent();
        then(foundAddress.get().getCustomerUuid()).isEqualTo(address.getCustomerUuid());
        then(foundAddress.get().getName()).isEqualTo(address.getName());
    }

    @Test
    @DisplayName("Given customer UUID and name, when findAddressByCustomerUuidAndName, then return empty if no address found")
    void givenCustomerUuidAndName_whenFindAddressByCustomerUuidAndName_thenReturnEmptyIfNoAddressFound() {
        // Given
        UUID customerUuid = UUID.randomUUID();
        String name = "Home";

        // When
        Optional<Address> foundAddress = addressRepository.findAddressByCustomerUuidAndName(customerUuid, name);

        // Then
        then(foundAddress).isNotPresent();
    }

    @Test
    @DisplayName("Given address UUID, when deleteByUuid, then remove address")
    void givenAddressUuid_whenDeleteByUuid_thenRemoveAddress() {
        addressRepository.save(address);



        UUID addressUuid = address.getUuid();
        addressRepository.deleteByUuid(addressUuid);

        Optional<Address> deletedAddress = addressRepository.findById(addressUuid);
        then(deletedAddress).isNotPresent();
    }
}