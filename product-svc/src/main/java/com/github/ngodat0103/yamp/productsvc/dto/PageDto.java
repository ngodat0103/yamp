package com.github.ngodat0103.yamp.productsvc.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Set;

@Builder
@Getter
public class PageDto<T extends RepresentationModel<T>> {
    private int page;
    private int size;
    private int totalElements;
    private int totalPages;
    private Set<T> data;
}
