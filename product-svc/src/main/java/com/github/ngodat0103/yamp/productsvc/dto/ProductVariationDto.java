package com.github.ngodat0103.yamp.productsvc.dto;
import lombok.Data;
import java.util.UUID;

@Data
public class ProductVariationDto {
    private UUID productVariationUuid;
    private String color;
    private String size;
    private String variant;
}
