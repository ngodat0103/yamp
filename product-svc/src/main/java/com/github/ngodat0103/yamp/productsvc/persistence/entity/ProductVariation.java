package com.github.ngodat0103.yamp.productsvc.persistence.entity;

import jakarta.persistence.*;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

/** The type Product variation. */
@Getter
@Setter
@Entity
public class ProductVariation extends BaseEntity {
  private UUID categoryUuid;
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "categoryUuid", updatable = false, insertable = false)
  private Category category;

  /** The Product variation options. */
  @OneToMany(mappedBy = "productVariation", fetch = FetchType.LAZY)
  Set<ProductVariationOption> productVariationOptions;
}
