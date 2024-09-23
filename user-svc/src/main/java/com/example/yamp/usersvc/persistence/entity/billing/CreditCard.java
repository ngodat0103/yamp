package com.example.yamp.usersvc.persistence.entity.billing;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.time.Month;
import java.time.Year;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue(value = "CC")
public class CreditCard extends BillingDetail {
  @Column(nullable = false)
  private String cardNumber;

  @Column(nullable = false)
  private String expirationDate;

  @Column(nullable = false)
  private String cvv;

  @Column(nullable = false)
  private String cardType;

  @Column(nullable = false)
  private String cardHolderName;

  @Column(nullable = false)
  private Month expirationMonth;

  @Column(nullable = false)
  private Year expirationYear;
}
