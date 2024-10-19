package com.github.ngodat0103.yamp.productsvc.dto;

import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Getter
public class PageDto<T extends RepresentationModel<T>> {
  private int page;
  private int size;
  private int totalElements;
  private int totalPages;
  private Set<T> data;
}
