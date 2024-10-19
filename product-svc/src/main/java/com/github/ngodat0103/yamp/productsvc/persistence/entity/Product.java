package com.github.ngodat0103.yamp.productsvc.persistence.entity;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** The type Product. */
@Getter
@Setter
@Entity
@NoArgsConstructor
public class Product extends BaseEntity {

  @Column(unique = true)
  private String name;

  @Column(length = 5000)
  private String description;

  private String imageUrl;

  @Column(unique = true)
  private String slugName;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "category_uuid")
  private Category category;

  /**
   * Instantiates a new Product.
   *
   * @param createBy the create by
   */
  public Product(UUID createBy) {
    super(createBy);
  }
}
