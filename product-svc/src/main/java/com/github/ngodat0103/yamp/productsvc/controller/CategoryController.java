package com.github.ngodat0103.yamp.productsvc.controller;

import com.github.ngodat0103.yamp.productsvc.dto.PageDto;
import com.github.ngodat0103.yamp.productsvc.dto.category.CategoryDtoRequest;
import com.github.ngodat0103.yamp.productsvc.dto.category.CategoryDtoResponse;
import com.github.ngodat0103.yamp.productsvc.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Category Management", description = "Operations related to category management")
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
  @Operation(
      summary = "Get all categories",
      description = "Retrieve a paginated list of all categories")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Successfully retrieved categories"),
    @ApiResponse(responseCode = "400", description = "Invalid request parameters")
  })
  @GetMapping(path = "/all")
  public PageDto<CategoryDtoResponse> getCategory(
      @Parameter(description = "Page number", example = "0")
          @RequestParam(required = false, defaultValue = "0")
          int page,
      @Parameter(description = "Page size", example = "100")
          @RequestParam(required = false, defaultValue = "100")
          int size) {
    return categoryService.getAllCategories(PageRequest.of(page, size));
  }

  /**
   * Gets category.
   *
   * @param slugName the slug name
   * @return the category
   */
  @Operation(
      summary = "Get category by slug name",
      description = "Retrieve a category by its slug name")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Successfully retrieved category"),
    @ApiResponse(responseCode = "404", description = "Category not found")
  })
  @GetMapping(path = "/{slugName}")
  public CategoryDtoResponse getCategory(
      @Parameter(description = "Slug name of the category") @PathVariable String slugName) {
    return categoryService.getCategory(slugName);
  }

  /**
   * Gets category by uuid.
   *
   * @param categoryUuid the category uuid
   * @return the category by uuid
   */
  @Operation(summary = "Get category by UUID", description = "Retrieve a category by its UUID")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Successfully retrieved category"),
    @ApiResponse(responseCode = "404", description = "Category not found")
  })
  @GetMapping
  public CategoryDtoResponse getCategoryByUuid(
      @Parameter(description = "UUID of the category") @RequestParam UUID categoryUuid) {
    return categoryService.getCategory(categoryUuid);
  }

  /**
   * Create category category dto response.
   *
   * @param categoryDtoRequest the category dto request
   * @return the category dto response
   */
  @Operation(
      summary = "Create a new category",
      description = "Create a new category with the provided details")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Successfully created category"),
    @ApiResponse(responseCode = "400", description = "Invalid request body")
  })
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
  @Operation(
      summary = "Update an existing category",
      description = "Update an existing category with the provided details")
  @ApiResponses({
    @ApiResponse(responseCode = "202", description = "Successfully updated category"),
    @ApiResponse(responseCode = "400", description = "Invalid request body"),
    @ApiResponse(responseCode = "404", description = "Category not found")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping(
      consumes = "application/json",
      produces = "application/json",
      path = "/{categoryUuid}")
  @SecurityRequirement(name = "oauth2")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public CategoryDtoResponse updateCategory(
      @RequestBody @Valid CategoryDtoRequest categoryDtoRequest,
      @Parameter(description = "UUID of the category to be updated") @PathVariable
          UUID categoryUuid) {
    return categoryService.updateCategory(categoryUuid, categoryDtoRequest);
  }

  /**
   * Delete category.
   *
   * @param categoryUuid the category uuid
   */
  @Operation(summary = "Delete a category", description = "Delete a category by its UUID")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Successfully deleted category"),
    @ApiResponse(responseCode = "404", description = "Category not found")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{categoryUuid}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @SecurityRequirement(name = "oauth2")
  public void deleteCategory(
      @Parameter(description = "UUID of the category to be deleted") @PathVariable
          UUID categoryUuid) {
    categoryService.deleteCategory(categoryUuid);
  }
}
