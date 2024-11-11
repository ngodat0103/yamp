package com.github.ngodat0103.yamp.productsvc.dto.product;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class ProductDtoRequest {
  private String name;
  private String description;
  private String categorySlug;
}
