package com.example.yamp.usersvc.persistence.repository.billing;

import com.example.yamp.usersvc.persistence.entity.billing.CreditCard;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, UUID> {}
