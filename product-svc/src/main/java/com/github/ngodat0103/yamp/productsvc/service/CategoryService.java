package com.github.ngodat0103.yamp.productsvc.service;
import com.github.ngodat0103.yamp.productsvc.dto.category.CategoryDtoRequest;
import com.github.ngodat0103.yamp.productsvc.dto.category.CategoryDtoResponse;
import com.github.ngodat0103.yamp.productsvc.dto.PageDto;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface CategoryService {
    CategoryDtoResponse createCategory(CategoryDtoRequest categoryDtoRequest);
    CategoryDtoResponse updateCategory(UUID categoryUuid, CategoryDtoRequest categoryDtoRequest);
    void deleteCategory(UUID uuid);
    CategoryDtoResponse getCategory(String categorySlugName);
    CategoryDtoResponse getCategory(UUID categoryUuid);
    PageDto<CategoryDtoResponse> getAllCategories(PageRequest pageRequest);
}
