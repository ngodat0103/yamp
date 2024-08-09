package org.example.productsvc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;
@Data
public class ProductCategoryDto {
    @JsonIgnore
    private UUID categoryUuid;
    @JsonIgnore
    private UUID parentCategoryUuid;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String name;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String parentCategoryName;
}
