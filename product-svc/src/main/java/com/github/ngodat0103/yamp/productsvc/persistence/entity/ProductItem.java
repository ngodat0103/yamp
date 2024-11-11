package com.github.ngodat0103.yamp.productsvc.persistence.entity;

import jakarta.persistence.*;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** The type Product item. */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProductItem extends BaseEntity {
  @Column(unique = true, nullable = false)
  private UUID productUuid;

  /** The Sku. */
  int SKU;

  /** The Quantity. */
  int quantity;

  /** The Price. */
  double price;

  /** The Product image. */
  String productImage;

  /** The Product. */
  @JoinColumn(name = "productUuid", insertable = false, updatable = false)
  @OneToOne
  Product product;

  /** The Product configurations. */
  @OneToMany(mappedBy = "productItem")
  Set<ProductConfiguration> productConfigurations;

  /**
   * Instantiates a new Product item.
   *
   * @param createdBy the created by
   */
  public ProductItem(UUID createdBy) {
    super(createdBy);
  }
}
