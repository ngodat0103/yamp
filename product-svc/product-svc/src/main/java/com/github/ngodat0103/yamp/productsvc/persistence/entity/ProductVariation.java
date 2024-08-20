package com.github.ngodat0103.yamp.productsvc.persistence.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
public class ProductVariation extends BaseEntity {
    private UUID categoryUuid;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryUuid",updatable = false,insertable = false)
    private ProductCategory category;



    @OneToMany(mappedBy = "productVariation", fetch = FetchType.LAZY)
    Set<ProductVariationOption> productVariationOptions;


}
