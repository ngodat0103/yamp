package com.github.ngodat0103.yamp.productsvc.controller;

import com.github.ngodat0103.yamp.productsvc.dto.PageDto;
import com.github.ngodat0103.yamp.productsvc.dto.category.CategoryDtoRequest;
import com.github.ngodat0103.yamp.productsvc.dto.category.CategoryDtoResponse;
import com.github.ngodat0103.yamp.productsvc.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/** The type Category controller. */
@RestController
@Slf4j
@RequestMapping(value = "/categories")
public class CategoryController {

  private final CategoryService categoryService;

  /**
   * Instantiates a new Category controller.
   *
   * @param categoryService the category service
   */
  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  /**
   * Gets category.
   *
   * @param page the page
   * @param size the size
   * @return the category
   */
  @GetMapping(path = "/all")
  public PageDto<CategoryDtoResponse> getCategory(
      @RequestParam(required = false, defaultValue = "0") int page,
      @RequestParam(required = false, defaultValue = "100") int size) {
    return categoryService.getAllCategories(PageRequest.of(page, size));
  }

  /**
   * Gets category.
   *
   * @param slugName the slug name
   * @return the category
   */
  @GetMapping(path = "/{slugName}")
  public CategoryDtoResponse getCategory(@PathVariable String slugName) {
    return categoryService.getCategory(slugName);
  }

  /**
   * Gets category by uuid.
   *
   * @param categoryUuid the category uuid
   * @return the category by uuid
   */
  @GetMapping
  public CategoryDtoResponse getCategoryByUuid(@RequestParam UUID categoryUuid) {
    return categoryService.getCategory(categoryUuid);
  }

  /**
   * Create category category dto response.
   *
   * @param categoryDtoRequest the category dto request
   * @return the category dto response
   */
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping(consumes = "application/json", produces = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  @SecurityRequirement(name = "oauth2")
  public CategoryDtoResponse createCategory(
      @RequestBody @Valid CategoryDtoRequest categoryDtoRequest) {
    return categoryService.createCategory(categoryDtoRequest);
  }

  /**
   * Update category category dto response.
   *
   * @param categoryDtoRequest the category dto request
   * @param categoryUuid the category uuid
   * @return the category dto response
   */
  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping(
      consumes = "application/json",
      produces = "application/json",
      path = "/{categoryUuid}")
  @SecurityRequirement(name = "oauth2")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public CategoryDtoResponse updateCategory(
      @RequestBody @Valid CategoryDtoRequest categoryDtoRequest, @PathVariable UUID categoryUuid) {
    return categoryService.updateCategory(categoryUuid, categoryDtoRequest);
  }

  /**
   * Delete category.
   *
   * @param categoryUuid the category uuid
   */
  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{categoryUuid}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @SecurityRequirement(name = "oauth2")
  public void deleteCategory(@PathVariable UUID categoryUuid) {
    categoryService.deleteCategory(categoryUuid);
  }
}
