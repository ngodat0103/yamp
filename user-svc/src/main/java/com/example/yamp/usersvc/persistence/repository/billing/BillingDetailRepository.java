package com.example.yamp.usersvc.persistence.repository.billing;

import com.example.yamp.usersvc.persistence.entity.billing.BillingDetail;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingDetailRepository<T extends BillingDetail> extends JpaRepository<T, UUID> {
  Set<T> findByCustomerUuid(UUID customerUuid);
}
