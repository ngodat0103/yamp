package com.example.userservice.persistence.repository.billing;

import com.example.userservice.persistence.entity.billing.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {
}
