package com.github.ngodat0103.yamp.productsvc.service.impl;

import static com.github.ngodat0103.yamp.productsvc.Util.*;

import com.github.ngodat0103.yamp.productsvc.dto.PageDto;
import com.github.ngodat0103.yamp.productsvc.dto.category.CategoryDtoRequest;
import com.github.ngodat0103.yamp.productsvc.dto.category.CategoryDtoResponse;
import com.github.ngodat0103.yamp.productsvc.dto.mapper.CategoryMapper;
import com.github.ngodat0103.yamp.productsvc.exception.NotFoundException;
import com.github.ngodat0103.yamp.productsvc.persistence.entity.Category;
import com.github.ngodat0103.yamp.productsvc.persistence.repository.CategoryRepository;
import com.github.ngodat0103.yamp.productsvc.service.CategoryService;
import com.github.slugify.Slugify;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/** The type Category service. */
@Service
@Slf4j
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;
  private final Slugify slugify;
  private final UriTemplate uriTemplate =
      UriTemplate.of("/api/v1/product/categories/{placeholder}");

  @Override
  @Transactional
  public CategoryDtoResponse createCategory(CategoryDtoRequest categoryDtoRequest) {
    UUID createBy = getAccountUuidFromAuthentication();
    String categoryName = StringUtils.capitalize(categoryDtoRequest.getName());
    if (categoryRepository.existsByName(categoryName)) {
      throwConflictException(log, "Category", "name", categoryName);
    }

    if (categoryDtoRequest.getParentCategoryUuid() != null) {
      UUID parentCategoryUuid = UUID.fromString(categoryDtoRequest.getParentCategoryUuid());
      if (!categoryRepository.existsById(parentCategoryUuid)) {
        throwNotFoundException(log, "Category", "parentCategoryUuid", parentCategoryUuid);
      }
    }
    Category category = categoryMapper.mapToEntity(categoryDtoRequest, createBy);
    category.setName(categoryName);
    category.setSlugName(slugify.slugify(category.getName()));
    log.debug("Creating category: {}", category);
    category = categoryRepository.save(category);
    log.debug("Category created: {}", category);
    return categoryMapper.mapToDto(category);
  }

  @Transactional
  @Override
  public CategoryDtoResponse updateCategory(
      UUID categoryUuid, CategoryDtoRequest categoryDtoRequest) {
    UUID parentCategoryUuid = null;
    UUID updateBy = getAccountUuidFromAuthentication();
    log.debug("Updating category: {}", categoryDtoRequest);
    Category currentCategory =
        categoryRepository
            .findById(categoryUuid)
            .orElseThrow(() -> new NotFoundException("Category not found"));

    if (categoryDtoRequest.getParentCategoryUuid() != null) {
      parentCategoryUuid = UUID.fromString(categoryDtoRequest.getParentCategoryUuid());
      if (!categoryRepository.existsById(parentCategoryUuid)) {
        throwNotFoundException(log, "Category", "parentCategoryUuid", parentCategoryUuid);
      }
    }

    currentCategory.setName(categoryDtoRequest.getName());
    currentCategory.setParentCategoryUuid(parentCategoryUuid);
    currentCategory.setSlugName(slugify.slugify(categoryDtoRequest.getName()));
    currentCategory.setLastModifiedBy(updateBy);
    currentCategory.setLastModifiedAt(LocalDateTime.now());
    currentCategory = categoryRepository.save(currentCategory);
    log.debug("Category updated: {}", currentCategory);
    return categoryMapper.mapToDto(currentCategory);
  }

  @Override
  public void deleteCategory(UUID uuid) {
    if (!categoryRepository.existsById(uuid)) {
      throw new NotFoundException("Category not found");
    }
    categoryRepository.deleteById(uuid);
  }

  @Override
  public CategoryDtoResponse getCategory(String categorySlugName) {
    Category category =
        categoryRepository
            .findCategoryBySlugName(categorySlugName)
            .orElseThrow(
                () -> {
                  String message =
                      String.format("Category with slugName %s not found", categorySlugName);
                  log.debug(message);
                  return new NotFoundException(message);
                });
    CategoryDtoResponse categoryDtoResponse = categoryMapper.mapToDto(category);
    addLinks(categoryDtoResponse, categorySlugName);
    return categoryDtoResponse;
  }

  @Override
  public CategoryDtoResponse getCategory(UUID categoryUuid) {
    Category category =
        categoryRepository
            .findById(categoryUuid)
            .orElseThrow(() -> new NotFoundException("Category not found"));
    CategoryDtoResponse categoryDtoResponse = categoryMapper.mapToDto(category);
    addLinks(categoryDtoResponse, category.getSlugName());
    return categoryDtoResponse;
  }

  @Override
  public PageDto<CategoryDtoResponse> getAllCategories(PageRequest pageRequest) {

    List<Category> categories = categoryRepository.findAll(pageRequest).getContent();
    log.debug("Get categories with {} elements", categories.size());
    Set<CategoryDtoResponse> categoryDtoResponses =
        categories.stream()
            .map(
                category -> {
                  CategoryDtoResponse categoryDtoResponse = categoryMapper.mapToDto(category);
                  addLinks(categoryDtoResponse, category.getSlugName());
                  return categoryDtoResponse;
                })
            .collect(Collectors.toUnmodifiableSet());
    int totalElements = (int) categoryRepository.count();

    return PageDto.<CategoryDtoResponse>builder()
        .data(categoryDtoResponses)
        .page(pageRequest.getPageNumber())
        .size(pageRequest.getPageSize())
        .totalElements(totalElements)
        .totalPages((totalElements / pageRequest.getPageSize()))
        .build();
  }

  private void addLinks(CategoryDtoResponse categoryDtoResponse, String slugName) {
    Link slugLink =
        Link.of(uriTemplate.expand(slugName).toString(), "SlugName")
            .withTitle("Get category by slugName")
            .withType("application/json");
    Link updateLink =
        Link.of(uriTemplate.expand(categoryDtoResponse.getUuid()).toString(), "Update")
            .withTitle("Update category")
            .withType("application/json");
    Link deleteLink =
        Link.of(uriTemplate.expand(categoryDtoResponse.getUuid()).toString(), "Delete")
            .withTitle("Delete category")
            .withType("application/json");
    if (categoryDtoResponse.getParentCategoryUuid() != null) {
      Link parentLink =
          Link.of(
                  uriTemplate.expand(categoryDtoResponse.getParentCategoryUuid()).toString(),
                  "Parent")
              .withTitle("Get parent category")
              .withType("application/json");
      categoryDtoResponse.add(parentLink);
    }
    categoryDtoResponse.add(slugLink, updateLink, deleteLink);
  }
}
