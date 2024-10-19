package com.github.ngodat0103.yamp.productsvc.dto;

import java.util.UUID;
import lombok.Data;

@Data
public class ProductVariationDto {
  private UUID productVariationUuid;
  private String color;
  private String size;
  private String variant;
}
