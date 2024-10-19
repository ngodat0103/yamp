package com.github.ngodat0103.yamp.productsvc.dto;

import java.util.UUID;
import lombok.Data;

@Data
public class ProductImageDto {
  private UUID productUuid;
  private String imageUrl;
}
