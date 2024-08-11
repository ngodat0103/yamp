package com.example.userservice.persistence.entity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;



@Entity
@Getter
@Setter
@AttributeOverride(name = "customerUuid", column = @Column(name = "BA_CUSTOMER_UUID"))
public class BankAccount extends BillingDetail{
    private String account;
    private String bankName;
    private String swift;
}
