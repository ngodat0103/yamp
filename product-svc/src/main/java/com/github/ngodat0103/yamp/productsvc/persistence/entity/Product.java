package com.github.ngodat0103.yamp.productsvc.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Product  extends BaseEntity{
    private String name;
    private String description;
    private String productImage;
    private UUID categoryUuid;


    @OneToOne
    @JoinColumn(name = "categoryUuid", insertable = false, updatable = false)
    private Category category;
    @OneToMany(mappedBy = "product",fetch = FetchType.EAGER)
    private Set<ProductImage> productImageSet;
    @OneToOne(mappedBy = "product" ,fetch = FetchType.EAGER)
    private ProductItem productItem;







}
