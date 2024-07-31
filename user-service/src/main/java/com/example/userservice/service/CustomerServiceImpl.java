package com.example.userservice.service;

import com.example.userservice.dto.AccountDto;
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
import org.springframework.web.client.RestTemplate;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.UUID;

import static com.example.userservice.constant.AuthServiceUri.*;

@Repository
public class CustomerServiceImpl implements CustomerService {


    private final Logger logger = org.slf4j.LoggerFactory.getLogger(CustomerServiceImpl.class);
    private final  CustomerRepository  customerRepository;
    private final AddressRepository addressRepository;
    private final RestTemplate restTemplate;
    private final CustomerMapper customerMapper;
    public CustomerServiceImpl(CustomerRepository customerRepository, AddressRepository addressRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
        this.customerMapper = customerMapper;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public void register(RegisterDto registerDto) {
        ResponseEntity<String> authSvcResponse =  this.restTemplate.postForEntity(AUTH_SVC_REG_URI,registerDto, String.class);
        logger.debug("Response from auth-service: {}", authSvcResponse);
        if(authSvcResponse.getStatusCode().is2xxSuccessful()){
            String accountUuidHeader = authSvcResponse.getHeaders().getFirst(ACCOUNT_UUID_HEADER);
            assert  accountUuidHeader !=null;
            logger.debug("get  Account UUID from auth-service: {}", accountUuidHeader);
            UUID accountUuid = UUID.fromString(accountUuidHeader);

            RequestEntity<Void> rq= RequestEntity.
                    post(AUTH_SVC_ROLE_URI).
                    header(ACCOUNT_UUID_HEADER,accountUuid.toString()).
                    header(ROLE_NAME_HEADER,DEFAULT_ROLE).
                    build();

            ResponseEntity<Void> authSvcRoleRp =  this.restTemplate.exchange(rq,Void.class);
            if(authSvcRoleRp.getStatusCode().is2xxSuccessful()){
                logger.debug("Role added to account: {}", accountUuid);
            }
            Customer customer = new Customer();
            customer.setAccountUuid(accountUuid);
            customer.setFirstName(registerDto.getFirstName());
            customer.setLastName(registerDto.getLastName());
            Customer cusResponse =  customerRepository.save(customer);
            logger.debug("Customer saved: {}", cusResponse);
        }


    }

    @Override
    public CustomerDto getCustomer(UUID accountUuid) throws AccountNotFoundException {
        Customer customer = customerRepository.findByAccountUuid(accountUuid);
        if(customer != null){
            CustomerDto customerDto = customerMapper.mapToDto(customer);
            RequestEntity<Void> rq = RequestEntity.
                    get(AUTH_SVC_ACC_URI).header(ACCOUNT_UUID_HEADER,accountUuid.toString()).
                    build();

            ResponseEntity<AccountDto> accountDtoResponse = this.restTemplate.exchange(rq,AccountDto.class);
            if (accountDtoResponse.getStatusCode().is2xxSuccessful()){
                AccountDto accountDto = accountDtoResponse.getBody();
                assert accountDto != null;
                customerDto.setAccount(accountDto);
                logger.debug("CustomerDto: {}", customerDto);
                return customerDto;
            }
            else {
                logger.error("Failed to get account from auth-service: {}", accountUuid);
            }

        }
        throw new AccountNotFoundException("Account not found: ");
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
