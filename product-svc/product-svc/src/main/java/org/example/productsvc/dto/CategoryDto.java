package org.example.productsvc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

import java.util.UUID;
@Data
public class CategoryDto {
    @JsonIgnore
    private UUID categoryUuid;
    @JsonIgnore
    private UUID parentCategoryUuid;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Category name is required")
    private String name;
    @Null
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String parentCategoryName;
}
