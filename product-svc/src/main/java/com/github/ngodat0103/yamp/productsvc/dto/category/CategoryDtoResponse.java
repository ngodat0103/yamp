package com.github.ngodat0103.yamp.productsvc.dto.category;

import java.util.UUID;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Getter
@EqualsAndHashCode(callSuper = true)
public class CategoryDtoResponse extends RepresentationModel<CategoryDtoResponse> {
  private UUID uuid;
  private UUID parentCategoryUuid;
  private String thumbnailUrl;
  private String name;
  private String slugName;
}
