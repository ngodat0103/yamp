package com.example.userservice.service;

import com.example.userservice.dto.AddressDto;
import com.example.userservice.dto.CustomerDto;
import com.example.userservice.dto.RegisterDto;
import com.example.userservice.dto.mapper.CustomerMapper;
import com.example.userservice.entity.Customer;
import com.example.userservice.repository.AddressRepository;
import com.example.userservice.repository.CustomerRepository;
import org.slf4j.Logger;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Repository
public class CustomerServiceImpl implements CustomerService {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(CustomerServiceImpl.class);
    private final  CustomerRepository  customerRepository;
    private final AddressRepository addressRepository;
    private final RestTemplate restTemplate;
    private final CustomerMapper customerMapper;
    private final String authSvcRegUri = "http://auth-service:8001/api/v1/auth/";
    public CustomerServiceImpl(CustomerRepository customerRepository, AddressRepository addressRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
        this.customerMapper = customerMapper;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public void register(RegisterDto registerDto) {
        ResponseEntity<String> authSvcResponse =  this.restTemplate.postForEntity(authSvcRegUri +"account/register",registerDto, String.class);
        if(authSvcResponse.getStatusCode().is2xxSuccessful()){
            String accountUuidHeader = authSvcResponse.getHeaders().getFirst("x-account-uuid");
            assert  accountUuidHeader !=null;
            logger.debug("get  Account UUID from auth-service: {}", accountUuidHeader);
            UUID accountUuid = UUID.fromString(accountUuidHeader);

            RequestEntity<Void> rq= RequestEntity.
                    post( authSvcRegUri+"account/role").
                    header("x-account-uuid",accountUuid.toString()).
                    header("x-role-name","ROLE_CUSTOMER").
                    build();

            ResponseEntity<Void> authSvcRoleRp =  this.restTemplate.exchange(rq,Void.class);
            if(authSvcRoleRp.getStatusCode().is2xxSuccessful()){
                logger.debug("Role added to account: {}", accountUuid);
            }

            Customer customer = new Customer();
            customer.setAccountUuid(accountUuid);
            customer.setFirstName(registerDto.getFirstName());
            customer.setLastName(registerDto.getLastName());
            customer.setEmail(registerDto.getEmail());
            customer.setUsername(registerDto.getUsername());
            Customer cusResponse =  customerRepository.save(customer);
            logger.debug("Customer saved: {}", cusResponse);
        }

    }

    @Override
    public CustomerDto getCustomer(UUID accountUuid) {
        Customer customer = customerRepository.findByAccountUuid(accountUuid);
        if(customer != null){
            return customerMapper.mapToDto(customer);
        }
        return null;
    }

    @Override
    public CustomerDto updateCustomer(UUID accountUuid, CustomerDto customerDto) {
        return null;
    }

    @Override
    public void deleteCustomer(UUID accountUuid) {

    }

    @Override
    public List<CustomerDto> getCustomers() {
        return List.of();
    }

    @Override
    public List<AddressDto> getAddresses(UUID accountUuid) {
        return List.of();
    }

    @Override
    public AddressDto getAddress(UUID accountUuid, UUID addressUuid) {
        return null;
    }

    @Override
    public AddressDto addAddress(UUID accountUuid, AddressDto addressDto) {
        return null;
    }

    @Override
    public AddressDto updateAddress(UUID accountUuid, UUID addressUuid, AddressDto addressDto) {
        return null;
    }

    @Override
    public void deleteAddress(UUID accountUuid, UUID addressUuid) {

    }
}
