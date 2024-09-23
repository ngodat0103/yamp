package com.example.yamp.usersvc.persistence.repository.billing;

import com.example.yamp.usersvc.persistence.entity.billing.BankAccount;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {}
