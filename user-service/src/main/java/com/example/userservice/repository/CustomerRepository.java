package com.example.userservice.repository;

import com.example.userservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer , UUID> {
    Customer save(Customer customer);
    Customer findByAccountUuid(UUID accountUuid);

}
