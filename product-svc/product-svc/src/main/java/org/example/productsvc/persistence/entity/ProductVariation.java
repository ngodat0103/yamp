package org.example.productsvc.persistence.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
public class ProductVariation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID productVariationUuid;
    @Column(nullable = false)
    private String color;
    private String size;
    private String variant;


    private UUID productUuid;
    @ManyToOne
    @JoinColumn(name = "productUuid", insertable = false, updatable = false)
    private Product product;
}
