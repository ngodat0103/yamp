package com.github.ngodat0103.yamp.productsvc.dto.category;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class CategoryDtoRequest {
    @NotNull(message = "Name is required")
    @Size(min = 3,max = 100,message = "Name must be between 3 and 100 characters")
    private String name;
    @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$",
            message = "UUID has to be represented by standard 36-char representation")
    private String parentCategoryUuid;
}
