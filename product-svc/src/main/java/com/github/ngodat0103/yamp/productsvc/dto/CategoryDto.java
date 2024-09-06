package com.github.ngodat0103.yamp.productsvc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;
@Data
@Builder
public class CategoryDto extends RepresentationModel<CategoryDto> {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID uuid;
    private UUID parentCategoryUuid;
    private String thumbnailUrl;
    @NotNull(message = "Category name is required")
    private String name;
}
