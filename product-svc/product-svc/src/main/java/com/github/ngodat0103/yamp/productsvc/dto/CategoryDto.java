package com.github.ngodat0103.yamp.productsvc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;
@Data
public class CategoryDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID uuid;
    private UUID parentCategoryUuid = null;
    @NotNull(message = "Category name is required")
    private String name;
}
