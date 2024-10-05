package com.example.yamp.usersvc.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.example.yamp.usersvc.dto.address.AddressDto;
import com.example.yamp.usersvc.dto.address.AddressResponseDto;
import com.example.yamp.usersvc.dto.mapper.AddressMapper;
import com.example.yamp.usersvc.exception.ConflictException;
import com.example.yamp.usersvc.exception.NotFoundException;
import com.example.yamp.usersvc.persistence.entity.Address;
import com.example.yamp.usersvc.persistence.entity.Customer;
import com.example.yamp.usersvc.persistence.repository.AddressRepository;
import com.example.yamp.usersvc.persistence.repository.CustomerRepository;
import com.example.yamp.usersvc.service.impl.AddressServiceImpl;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

  @Mock private AddressRepository addressRepository;
  @Mock private CustomerRepository customerRepository;
  @Mock private AddressMapper addressMapper;
  @Mock private SecurityContext securityContext;
  @Mock private Authentication authentication;

  @InjectMocks private AddressServiceImpl addressServiceImpl;

  private UUID customerUuid;
  private UUID addressUuid;
  private AddressDto addressDto;
  private Address address;
  private Customer customer;

  @BeforeEach
  void setUp() {
    customerUuid = UUID.randomUUID();
    addressUuid = UUID.randomUUID();
    addressDto =
        AddressDto.builder()
            .name("Home")
            .cityName("City")
            .phoneNumber("1234567890")
            .province("Province")
            .street("Street")
            .ward("Ward")
            .district("District")
            .addressType("Type")
            .build();
    address = new Address();
    address.setUuid(addressUuid);
    address.setCustomerUuid(customerUuid);
    address.setName("Home");
    address.setCityName("City");
    address.setPhoneNumber("1234567890");
    address.setProvince("Province");
    address.setStreet("Street");
    address.setWard("Ward");
    address.setDistrict("District");
    address.setAddressType("Type");

    customer = new Customer();
    customer.setCustomerUuid(customerUuid);
    customer.setFirstName("John");
    customer.setLastName("Doe");
    customer.setPhoneNumber("1234567890");
    customer.setCreateAtDate(LocalDateTime.now());
    customer.setLastModifiedDate(LocalDateTime.now());
    given(securityContext.getAuthentication()).willReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    given(authentication.getName()).willReturn(customerUuid.toString());
  }

  @Test
  void givenCustomerExistsAndNoAddressConflict_whenCreateAddress_thenSaveAddress() {
    // given
    given(customerRepository.findCustomerByCustomerUuid(customerUuid))
        .willReturn(Optional.of(customer));
    given(addressRepository.findAddressByCustomerUuidAndName(customerUuid, addressDto.getName()))
        .willReturn(Optional.empty());
    given(addressMapper.mapToEntity(addressDto)).willReturn(address);

    // when
    addressServiceImpl.createAddress(addressDto);

    // then
    then(customerRepository).should().save(customer);
    then(addressRepository).should().save(address);
  }

  @Test
  void givenCustomerNotFound_whenCreateAddress_thenThrowNotFoundException() {
    // given
    given(customerRepository.findCustomerByCustomerUuid(customerUuid)).willReturn(Optional.empty());

    // when & then
    assertThatThrownBy(() -> addressServiceImpl.createAddress(addressDto))
        .isInstanceOf(NotFoundException.class);
  }

  @Test
  void givenAddressNameConflict_whenCreateAddress_thenThrowConflictException() {
    // given
    given(customerRepository.findCustomerByCustomerUuid(customerUuid))
        .willReturn(Optional.of(customer));
    given(addressRepository.findAddressByCustomerUuidAndName(customerUuid, addressDto.getName()))
        .willReturn(Optional.of(address));

    // when & then
    assertThatThrownBy(() -> addressServiceImpl.createAddress(addressDto))
        .isInstanceOf(ConflictException.class);
  }

  @Test
  void givenCustomerExistsAndAddressesExist_whenGetAddresses_thenReturnAddresses() {
    // given
    given(customerRepository.findCustomerByCustomerUuid(customerUuid))
        .willReturn(Optional.of(customer));
    given(addressRepository.findAddressByCustomerUuid(customerUuid)).willReturn(Set.of(address));
    given(addressMapper.mapToDtos(Set.of(address))).willReturn(Set.of(addressDto));

    // when
    AddressResponseDto response = addressServiceImpl.getAddresses();

    // then
    assertThat(response.getCustomerUuid()).isEqualTo(customerUuid);
    assertThat(response.getAddresses()).containsExactly(addressDto);
    assertThat(response.getCurrentElements()).isEqualTo(1);
    assertThat(response.getTotalElements()).isEqualTo(1);
    assertThat(response.getCurrentPage()).isEqualTo(1);
    assertThat(response.getTotalPages()).isEqualTo(1);
  }

  @Test
  void givenCustomerNotFound_whenGetAddresses_thenThrowNotFoundException() {
    // given
    given(customerRepository.findCustomerByCustomerUuid(customerUuid)).willReturn(Optional.empty());

    // when & then
    assertThatThrownBy(() -> addressServiceImpl.getAddresses())
        .isInstanceOf(NotFoundException.class);
  }

  @Test
  void givenCustomerExistsButNoAddresses_whenGetAddresses_thenReturnEmptyAddresses() {
    // given
    given(customerRepository.findCustomerByCustomerUuid(customerUuid))
        .willReturn(Optional.of(customer));
    given(addressRepository.findAddressByCustomerUuid(customerUuid)).willReturn(Set.of());
    given(addressMapper.mapToDtos(Set.of())).willReturn(Set.of());

    // when
    AddressResponseDto response = addressServiceImpl.getAddresses();

    // then
    assertThat(response.getCustomerUuid()).isEqualTo(customerUuid);
    assertThat(response.getAddresses()).isEmpty();
    assertThat(response.getCurrentElements()).isEqualTo(0);
    assertThat(response.getTotalElements()).isEqualTo(0);
    assertThat(response.getCurrentPage()).isEqualTo(1);
    assertThat(response.getTotalPages()).isEqualTo(1);
  }

  @Test
  void givenCustomerAndAddressFound_whenUpdateAddress_thenUpdateAddress() {
    // given
    given(customerRepository.findCustomerByCustomerUuid(customerUuid))
        .willReturn(Optional.of(customer));
    given(addressRepository.findById(addressUuid)).willReturn(Optional.of(address));
    given(addressMapper.mapToEntity(addressDto)).willReturn(address);

    // when
    addressServiceImpl.updateAddress(addressUuid, addressDto);

    // then
    then(addressRepository).should().save(address);
    then(customerRepository).should().save(customer);
  }

  @Test
  void givenCustomerNotFound_whenUpdateAddress_thenThrowNotFoundException() {
    // given
    given(customerRepository.findCustomerByCustomerUuid(customerUuid)).willReturn(Optional.empty());

    // when & then
    assertThatThrownBy(() -> addressServiceImpl.updateAddress(addressUuid, addressDto))
        .isInstanceOf(NotFoundException.class);
  }

  @Test
  void givenAddressNotFound_whenUpdateAddress_thenThrowNotFoundException() {
    // given
    given(customerRepository.findCustomerByCustomerUuid(customerUuid))
        .willReturn(Optional.of(customer));
    given(addressRepository.findById(addressUuid)).willReturn(Optional.empty());

    // when & then
    assertThatThrownBy(() -> addressServiceImpl.updateAddress(addressUuid, addressDto))
        .isInstanceOf(NotFoundException.class);
  }

  @Test
  void givenCustomerAndAddressFound_whenDeleteAddress_thenDeleteAddress() {
    // given
    given(customerRepository.findCustomerByCustomerUuid(customerUuid))
        .willReturn(Optional.of(customer));
    given(addressRepository.existsById(addressUuid)).willReturn(true);

    // when
    addressServiceImpl.deleteAddress(addressUuid);

    // then
    then(addressRepository).should().deleteByUuid(addressUuid);
  }

  @Test
  void givenCustomerNotFound_whenDeleteAddress_thenThrowNotFoundException() {
    // given
    given(customerRepository.findCustomerByCustomerUuid(customerUuid)).willReturn(Optional.empty());

    // when & then
    assertThatThrownBy(() -> addressServiceImpl.deleteAddress(addressUuid))
        .isInstanceOf(NotFoundException.class);
  }

  @Test
  void givenAddressNotFound_whenDeleteAddress_thenThrowNotFoundException() {
    // given
    given(customerRepository.findCustomerByCustomerUuid(customerUuid))
        .willReturn(Optional.of(customer));
    given(addressRepository.existsById(addressUuid)).willReturn(false);

    // when & then
    assertThatThrownBy(() -> addressServiceImpl.deleteAddress(addressUuid))
        .isInstanceOf(NotFoundException.class);
  }
}
