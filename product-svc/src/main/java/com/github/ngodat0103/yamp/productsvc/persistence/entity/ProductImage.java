package com.github.ngodat0103.yamp.productsvc.persistence.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
public class ProductImage extends BaseEntity {
    @Getter
    @Setter
    @EqualsAndHashCode
    static class ProductImageId implements Serializable {
        private UUID productUuid;
        private String imageUrl;
    }
    @Id
    ProductImageId productImageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productUuid")
    private Product product;
}
