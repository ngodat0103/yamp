package com.example.userservice.persistence.entity;


import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AttributeOverride(name = "customerUuid", column = @Column(name = "CC_CUSTOMER_UUID"))
public class CreditCard  extends BillingDetail {
    @NotNull
    private String cardNumber;
    @NotNull
    private String expirationDate;
    @NotNull
    private String cvv;
    @NotNull
    private String cardType;
    @NotNull
    private String cardHolderName;
}
