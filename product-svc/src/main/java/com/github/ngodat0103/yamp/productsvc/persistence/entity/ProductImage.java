package com.github.ngodat0103.yamp.productsvc.persistence.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/** The type Product image. */
@Getter
@Setter
@Entity
public class ProductImage extends BaseEntity {
  /** The type Product image id. */
  @Getter
  @Setter
  @EqualsAndHashCode
  static class ProductImageId implements Serializable {
    private UUID productUuid;
    private String imageUrl;
  }

  /** The Product image id. */
  @Id ProductImageId productImageId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "productUuid")
  private Product product;
}
