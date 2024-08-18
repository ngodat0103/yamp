package com.example.yamp.usersvc.persistence.entity.billing;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import java.time.Month;
import java.time.Year;

@Entity
@Getter
@Setter
@DiscriminatorValue(value = "CC")
public class CreditCard  extends BillingDetail {
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
