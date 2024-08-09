package org.example.productsvc.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Primary;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
public class ProductImage {
    @Getter
    @Setter
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
