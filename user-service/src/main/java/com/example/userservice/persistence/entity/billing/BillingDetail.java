package com.example.userservice.persistence.entity.billing;
import com.example.userservice.persistence.entity.Customer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "BD_TYPE")
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"customerUuid", "name"})
})
@Getter
@Setter
public abstract class BillingDetail   {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID billingUuid;
    @Column(nullable = false)
    private UUID customerUuid;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private LocalDateTime createAtDate;
    @Column(nullable = false)
    private LocalDateTime lastModifiedDate;

    @ManyToOne
    @JoinColumn(name = "customerUuid", insertable = false, updatable = false)
    Customer customer;
}
