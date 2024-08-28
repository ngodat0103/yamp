package com.example.yamp.usersvc.persistence.repository.billing;

import com.example.yamp.usersvc.persistence.entity.billing.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CreditCardRepository extends JpaRepository<CreditCard, UUID> {
}
