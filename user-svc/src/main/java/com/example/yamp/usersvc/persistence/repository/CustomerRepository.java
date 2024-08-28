package com.example.yamp.usersvc.persistence.repository;

import com.example.yamp.usersvc.persistence.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Optional<Customer> findCustomerByCustomerUuid(UUID customerUuid );
}
