package org.example.productsvc.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class ProductBrand {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID brandUuid;
    @Column(unique = true)
    private String name;
    private String logo;
}
