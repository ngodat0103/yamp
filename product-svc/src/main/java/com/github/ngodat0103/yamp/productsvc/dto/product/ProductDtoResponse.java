package com.github.ngodat0103.yamp.productsvc.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@EqualsAndHashCode(callSuper = true)
public class ProductDtoResponse extends RepresentationModel<ProductDtoResponse> {

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
