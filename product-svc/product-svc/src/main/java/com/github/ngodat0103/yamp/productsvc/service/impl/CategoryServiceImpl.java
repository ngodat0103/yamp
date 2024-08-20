package com.github.ngodat0103.yamp.productsvc.service.impl;
import com.github.ngodat0103.yamp.productsvc.dto.CategoryDto;
import com.github.ngodat0103.yamp.productsvc.dto.mapper.CategoryMapper;
import com.github.ngodat0103.yamp.productsvc.persistence.entity.Category;
import com.github.ngodat0103.yamp.productsvc.persistence.repository.CategoryRepository;
import com.github.ngodat0103.yamp.productsvc.service.CategoryService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.mapToEntity(categoryDto);
        log.debug("Creating category: {}", category);
         category =   categoryRepository.save(category);
        log.debug("Category created: {}", category);
        return categoryMapper.mapToDto(category);
    }

    @Transactional
    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto,UUID accountUuid) {
        log.debug("Updating category: {}", categoryDto);
        Category currentCategory = categoryRepository.findById(categoryDto.getUuid()).orElseThrow(() -> new RuntimeException("Category not found"));

        if(!categoryRepository.existsById(categoryDto.getParentCategoryUuid())){
            log.debug("Parent category not found, Roll back");
            throw new RuntimeException("Parent category not found");
        }
        currentCategory.setName(categoryDto.getName());
        currentCategory.setParentCategoryUuid(categoryDto.getParentCategoryUuid());
        currentCategory.setLastModifiedBy(accountUuid);
        currentCategory.setLastModifiedAt(LocalDateTime.now());
        currentCategory = categoryRepository.save(currentCategory);
        return categoryMapper.mapToDto(currentCategory);
    }

    @Override
    public void deleteCategory(UUID uuid) {
        categoryRepository.deleteById(uuid);
    }

    @Override
    public CategoryDto getCategory(String categoryName) {
        Category category = categoryRepository.findCategoryByName(categoryName).orElseThrow(() -> new RuntimeException("Category not found"));
        return categoryMapper.mapToDto(category);
    }

    @Override
    public CategoryDto getCategory(UUID categoryUuid) {
        Category category = categoryRepository.findById(categoryUuid).orElseThrow(() -> new RuntimeException("Category not found"));
        return categoryMapper.mapToDto(category);
    }
}
