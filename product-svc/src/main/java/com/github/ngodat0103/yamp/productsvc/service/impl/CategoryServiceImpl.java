package com.github.ngodat0103.yamp.productsvc.service.impl;
import com.github.ngodat0103.yamp.productsvc.dto.CategoryDto;
import com.github.ngodat0103.yamp.productsvc.dto.mapper.CategoryMapper;
import com.github.ngodat0103.yamp.productsvc.exception.ConflictException;
import com.github.ngodat0103.yamp.productsvc.exception.NotFoundException;
import com.github.ngodat0103.yamp.productsvc.persistence.entity.Category;
import com.github.ngodat0103.yamp.productsvc.persistence.repository.CategoryRepository;
import com.github.ngodat0103.yamp.productsvc.service.CategoryService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    @Override
    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto) {
        UUID createBy = getAccountUuidFromAuthentication();
        if(categoryRepository.existsByName(categoryDto.getName())){
            log.debug("Category already exists, Roll back");
            throw new ConflictException("Category name is already existed");
        }

        Category category = categoryMapper.mapToEntity(categoryDto,createBy);

        log.debug("Creating category: {}", category);
         category =   categoryRepository.save(category);
        log.debug("Category created: {}", category);
        return categoryMapper.mapToDto(category);
    }

    @Transactional
    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        UUID updateBy = getAccountUuidFromAuthentication();
        UUID uuid = categoryDto.getUuid();
        UUID parentCategoryUuid = categoryDto.getParentCategoryUuid();


        log.debug("Updating category: {}", categoryDto);
        Category currentCategory = categoryRepository
                .findById(uuid).orElseThrow(() -> new NotFoundException("Category not found"));


        if(parentCategoryUuid!=null){
            if(!categoryRepository.existsById(parentCategoryUuid)){
                log.debug("Parent category not found, Roll back");
                throw new NotFoundException("Parent category not found");
            }
        }

        currentCategory.updateCategory(categoryDto, updateBy);
        currentCategory = categoryRepository.save(currentCategory);
        return categoryMapper.mapToDto(currentCategory);
    }

    @Override
    public void deleteCategory(UUID uuid) {
        categoryRepository.deleteById(uuid);
    }

    @Override
    public CategoryDto getCategory(String categoryName) {
        Category category = categoryRepository.findCategoryByName(categoryName).orElseThrow(() -> new NotFoundException("Category not found"));
        return categoryMapper.mapToDto(category);
    }

    @Override
    public CategoryDto getCategory(UUID categoryUuid) {
        Category category = categoryRepository.findById(categoryUuid).orElseThrow(() -> new NotFoundException("Category not found"));
        return categoryMapper.mapToDto(category);
    }

    @Override
    public Set<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        log.debug("Get categories with {} elements", categories.size());
        return categories.stream().map(categoryMapper::mapToDto).collect(Collectors.toSet());
    }


    private UUID getAccountUuidFromAuthentication(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Assert.notNull(authentication,"Authentication object is required");
        return UUID.fromString(authentication.getName());
    }
}
