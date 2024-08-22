package com.github.ngodat0103.yamp.productsvc.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;
@Data
@Builder
public class CategoryDto {

    private UUID uuid;
    private UUID parentCategoryUuid;
    @NotNull(message = "Category name is required")
    private String name;
}
