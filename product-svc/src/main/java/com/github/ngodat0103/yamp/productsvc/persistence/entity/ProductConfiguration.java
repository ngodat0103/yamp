package com.github.ngodat0103.yamp.productsvc.persistence.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
public class ProductConfiguration {
    @Embeddable
    public static class CompositePK implements Serializable {
       private UUID productItemUuid;
       private UUID productVariationOptionUUid;
    }

    @EmbeddedId
    private CompositePK id;


    @ManyToOne
    @JoinColumn(name = "productItemUuid", insertable = false, updatable = false)
    private ProductItem productItem;

    @ManyToOne
    @JoinColumn(name = "productVariationOptionUUid", insertable = false, updatable = false)
    private ProductVariationOption productVariationOption;

}
