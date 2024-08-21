package com.github.ngodat0103.yamp.productsvc.service;
import com.github.ngodat0103.yamp.productsvc.dto.CategoryDto;

import java.util.Set;
import java.util.UUID;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto,UUID createBy);
    CategoryDto updateCategory(CategoryDto categoryDto,UUID updateBy);
    void deleteCategory(UUID uuid);
    CategoryDto getCategory(String categoryName);
    CategoryDto getCategory(UUID categoryUuid);
    Set<CategoryDto> getAllCategories();
}
