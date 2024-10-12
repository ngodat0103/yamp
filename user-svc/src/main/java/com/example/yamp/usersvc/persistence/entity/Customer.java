package com.example.yamp.usersvc.persistence.entity;

import com.example.yamp.usersvc.persistence.entity.billing.BillingDetail;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Customer {
  @Id
  private UUID customerUuid;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Column(unique = true)
  private String phoneNumber;

  private LocalDateTime createAtDate = LocalDateTime.now();
  private LocalDateTime lastModifiedDate = LocalDateTime.now();

  @OneToMany(mappedBy = "customer")
  private Set<Address> addresses;

  @OneToMany(mappedBy = "customer")
  private Set<BillingDetail> billingDetails;
}
