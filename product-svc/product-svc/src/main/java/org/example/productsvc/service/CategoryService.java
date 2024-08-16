package org.example.productsvc.service;
import org.example.productsvc.dto.CategoryDto;
import java.util.UUID;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto);
    void deleteCategory();
    CategoryDto getCategory(String categoryName);
    CategoryDto getCategory(UUID categoryUuid);
}
