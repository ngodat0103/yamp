package com.github.ngodat0103.yamp.productsvc.dto;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class ProductDto {
    private UUID productUuid;
    private String name;
    private String shortDescription;
    private String longDescription;


    CategoryDto productCategory;
    Set<ProductVariationDto> productVariationSet;
    Set<ProductImageDto> productImageSet;
}
