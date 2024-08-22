package com.github.ngodat0103.yamp.productsvc.persistence.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProductItem extends BaseEntity {
    @Column(unique = true,nullable = false)
    private UUID productUuid;
    int SKU;
    int quantity;
    double price;

    @JoinColumn(name = "productUuid", insertable = false, updatable = false)
    @OneToOne
    Product product;




    @OneToMany(mappedBy = "productItem")
    Set<ProductConfiguration> productConfigurations;


    public ProductItem(UUID createdBy) {
        super(createdBy);
    }

}
