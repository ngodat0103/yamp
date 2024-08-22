package com.example.yamp.usersvc.repository;


import com.example.yamp.usersvc.persistence.entity.Customer;
import com.example.yamp.usersvc.persistence.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("unit-test")
public class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void givenCustomer_whenSave_thenReturnSavedCustomer(){
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        Customer savedCustomer = customerRepository.save(customer);
        Assertions.assertEquals(customer.getFirstName(), savedCustomer.getFirstName());
        Assertions.assertEquals(customer.getLastName(), savedCustomer.getLastName());
        Assertions.assertNotNull(savedCustomer.getCustomerUuid());
    }
}
