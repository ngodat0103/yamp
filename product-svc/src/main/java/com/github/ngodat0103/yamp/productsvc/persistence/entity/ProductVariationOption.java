package com.github.ngodat0103.yamp.productsvc.persistence.entity;

import jakarta.persistence.*;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** The type Product variation option. */
@NoArgsConstructor
@Getter
@Entity
@Setter
public class ProductVariationOption extends BaseEntity {
  private UUID variationUuid;

  @Column(nullable = false)
  private String value;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "variationUuid", updatable = false, insertable = false)
  private ProductVariation productVariation;

  @OneToMany(mappedBy = "productVariationOption")
  private Set<ProductConfiguration> productConfigurations;
}
