package com.github.ngodat0103.yamp.productsvc.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ProductDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String uuid;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String slugName;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String imageUrl;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID createdBy;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID lastModifiedBy;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime lastModifiedAt;


    @NotNull
    private String name;
    private String description;
    @NotNull
    private String categorySlug;
}
