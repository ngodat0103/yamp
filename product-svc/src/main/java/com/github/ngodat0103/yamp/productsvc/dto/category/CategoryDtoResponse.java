package com.github.ngodat0103.yamp.productsvc.dto.category;

import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;
@Builder
@Getter
public class CategoryDtoResponse extends RepresentationModel<CategoryDtoResponse> {
    private UUID uuid;
    private UUID parentCategoryUuid;
    private String thumbnailUrl;
    private String name;
    private String slugName;
}
