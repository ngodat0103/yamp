package com.github.ngodat0103.yamp.productsvc.service.impl;
import com.github.ngodat0103.yamp.productsvc.dto.CategoryDto;
import com.github.ngodat0103.yamp.productsvc.dto.mapper.CategoryMapper;
import com.github.ngodat0103.yamp.productsvc.exception.ConflictException;
import com.github.ngodat0103.yamp.productsvc.exception.NotFoundException;
import com.github.ngodat0103.yamp.productsvc.persistence.entity.Category;
import com.github.ngodat0103.yamp.productsvc.persistence.repository.CategoryRepository;
import com.github.ngodat0103.yamp.productsvc.service.CategoryService;
import com.github.slugify.Slugify;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.github.ngodat0103.yamp.productsvc.Util.getAccountUuidFromAuthentication;
import static com.github.ngodat0103.yamp.productsvc.Util.throwNotFoundException;

@Service
@Slf4j
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final Slugify slugify;


    @Override
    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto) {
        UUID createBy = getAccountUuidFromAuthentication();
        if(categoryRepository.existsByName(categoryDto.getName())){
            log.debug("Category already exists, Roll back");
            throw new ConflictException("Category name is already existed");
        }
        UUID parentUuid = categoryDto.getParentCategoryUuid();
        if(parentUuid!=null && !categoryRepository.existsByParentCategoryUuid(categoryDto.getParentCategoryUuid())){
            throwNotFoundException(log,"Category","parentCategoryUuid",parentUuid);
        }

        Category category = categoryMapper.mapToEntity(categoryDto,createBy);
        category.setSlugName(slugify.slugify(category.getName()));

        log.debug("Creating category: {}", category);
         category =   categoryRepository.save(category);
        log.debug("Category created: {}", category);
        return categoryMapper.mapToDto(category);
    }

    @Transactional
    @Override
    public CategoryDto updateCategory(UUID categoryUuid,CategoryDto categoryDto) {
        UUID updateBy = getAccountUuidFromAuthentication();
        UUID parentCategoryUuid = categoryDto.getParentCategoryUuid();
        log.debug("Updating category: {}", categoryDto);
        Category currentCategory = categoryRepository
                .findById(categoryUuid).orElseThrow(() -> new NotFoundException("Category not found"));
        if(parentCategoryUuid!=null){
            if(!categoryRepository.existsById(parentCategoryUuid)){
                log.debug("Parent category not found, Roll back");
                throw new NotFoundException("Parent category not found");
            }
        }


        currentCategory.setName(categoryDto.getName());
        currentCategory.setThumbnailUrl(categoryDto.getThumbnailUrl());
        currentCategory.setParentCategoryUuid(parentCategoryUuid);
        currentCategory.setSlugName(slugify.slugify(categoryDto.getName()));

        currentCategory.setLastModifiedBy(updateBy);
        currentCategory.setLastModifiedAt(LocalDateTime.now());
        currentCategory = categoryRepository.save(currentCategory);
        log.debug("Category updated: {}", currentCategory);
        return categoryMapper.mapToDto(currentCategory);
    }

    @Override
    public void deleteCategory(UUID uuid) {
        if(!categoryRepository.existsById(uuid)){
            throw new NotFoundException("Category not found");
        }
        categoryRepository.deleteById(uuid);
    }

    @Override
    public CategoryDto getCategory(String categorySlugName) {
        Category category = categoryRepository.findCategoryBySlugName(categorySlugName).orElseThrow(() -> {
            String message = String.format("Category with slugName %s not found", categorySlugName);
            log.debug(message);
            return new NotFoundException(message);
        });
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


}
