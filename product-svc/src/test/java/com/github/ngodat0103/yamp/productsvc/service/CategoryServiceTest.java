package com.github.ngodat0103.yamp.productsvc.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;

import com.github.ngodat0103.yamp.productsvc.dto.PageDto;
import com.github.ngodat0103.yamp.productsvc.dto.category.CategoryDtoRequest;
import com.github.ngodat0103.yamp.productsvc.dto.category.CategoryDtoResponse;
import com.github.ngodat0103.yamp.productsvc.dto.mapper.CategoryMapper;
import com.github.ngodat0103.yamp.productsvc.exception.ConflictException;
import com.github.ngodat0103.yamp.productsvc.exception.NotFoundException;
import com.github.ngodat0103.yamp.productsvc.persistence.entity.Category;
import com.github.ngodat0103.yamp.productsvc.persistence.repository.CategoryRepository;
import com.github.ngodat0103.yamp.productsvc.service.impl.CategoryServiceImpl;
import com.github.slugify.Slugify;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

  @InjectMocks private CategoryServiceImpl categoryService;

  @Mock private CategoryRepository categoryRepository;

  @Mock private CategoryMapper categoryMapper;

  @Mock private Slugify slugify;

  @Mock private SecurityContext securityContext;

  @Mock private Authentication authentication;

  private CategoryDtoRequest categoryDtoRequest;
  private Category category;
  private CategoryDtoResponse categoryDtoResponse;
  private UUID categoryUuid;

  @BeforeEach
  void setUp() {
    categoryDtoRequest =
        CategoryDtoRequest.builder()
            .name("Test Category")
            .parentCategoryUuid(UUID.randomUUID().toString())
            .build();

    category = new Category(UUID.randomUUID());
    category.setName("Test Category");
    category.setSlugName("test-category");

    categoryDtoResponse =
        CategoryDtoResponse.builder()
            .uuid(UUID.randomUUID())
            .name("Test Category")
            .slugName("test-category")
            .build();

    categoryUuid = UUID.randomUUID();

    // Mock SecurityContext and Authentication
    lenient().when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    lenient().when(authentication.getName()).thenReturn(UUID.randomUUID().toString());
  }

  @Test
  void testCreateCategoryWhenCategoryWithSameNameExistsThenThrowConflictException() {
    // Arrange
    given(categoryRepository.existsByName(anyString())).willReturn(true);

    // Act & Assert
    assertThatThrownBy(() -> categoryService.createCategory(categoryDtoRequest))
        .isInstanceOf(ConflictException.class);

    then(categoryRepository).should(times(1)).existsByName(anyString());
  }

  @Test
  void testCreateCategoryWhenParentCategoryDoesNotExistThenThrowNotFoundException() {
    // Arrange
    given(categoryRepository.existsByName(anyString())).willReturn(false);
    given(categoryRepository.existsById(any(UUID.class))).willReturn(false);

    // Act & Assert
    assertThatThrownBy(() -> categoryService.createCategory(categoryDtoRequest))
        .isInstanceOf(NotFoundException.class);

    then(categoryRepository).should(times(1)).existsByName(anyString());
    then(categoryRepository).should(times(1)).existsById(any(UUID.class));
  }

  @Test
  void testCreateCategoryWhenCategoryIsSuccessfullyCreatedThenReturnCategoryDtoResponse() {
    // Arrange
    given(categoryRepository.existsByName(anyString())).willReturn(false);
    given(categoryRepository.existsById(any(UUID.class))).willReturn(true);
    given(categoryMapper.mapToEntity(any(CategoryDtoRequest.class), any(UUID.class)))
        .willReturn(category);
    given(slugify.slugify(anyString())).willReturn("test-category");
    given(categoryRepository.save(any(Category.class))).willReturn(category);
    given(categoryMapper.mapToDto(any(Category.class))).willReturn(categoryDtoResponse);

    // Act
    CategoryDtoResponse response = categoryService.createCategory(categoryDtoRequest);

    // Assert
    assertThat(response).isEqualTo(categoryDtoResponse);

    then(categoryRepository).should(times(1)).existsByName(anyString());
    then(categoryRepository).should(times(1)).existsById(any(UUID.class));
    then(categoryMapper)
        .should(times(1))
        .mapToEntity(any(CategoryDtoRequest.class), any(UUID.class));
    then(slugify).should(times(1)).slugify(anyString());
    then(categoryRepository).should(times(1)).save(any(Category.class));
    then(categoryMapper).should(times(1)).mapToDto(any(Category.class));
  }

  @Test
  void testUpdateCategoryWhenCategoryDoesNotExistThenThrowNotFoundException() {
    // Arrange
    given(categoryRepository.findById(any(UUID.class))).willReturn(Optional.empty());

    // Act & Assert
    assertThatThrownBy(() -> categoryService.updateCategory(categoryUuid, categoryDtoRequest))
        .isInstanceOf(NotFoundException.class);

    then(categoryRepository).should(times(1)).findById(any(UUID.class));
  }

  @Test
  void testUpdateCategoryWhenParentCategoryDoesNotExistThenThrowNotFoundException() {
    // Arrange
    given(categoryRepository.findById(any(UUID.class))).willReturn(Optional.of(category));
    given(categoryRepository.existsById(any(UUID.class))).willReturn(false);

    // Act & Assert
    assertThatThrownBy(() -> categoryService.updateCategory(categoryUuid, categoryDtoRequest))
        .isInstanceOf(NotFoundException.class);

    then(categoryRepository).should(times(1)).findById(any(UUID.class));
    then(categoryRepository).should(times(1)).existsById(any(UUID.class));
  }

  @Test
  void testUpdateCategoryWhenCategoryIsSuccessfullyUpdatedThenReturnCategoryDtoResponse() {
    // Arrange
    given(categoryRepository.findById(any(UUID.class))).willReturn(Optional.of(category));
    given(categoryRepository.existsById(any(UUID.class))).willReturn(true);
    given(slugify.slugify(anyString())).willReturn("test-category");
    given(categoryRepository.save(any(Category.class))).willReturn(category);
    given(categoryMapper.mapToDto(any(Category.class))).willReturn(categoryDtoResponse);

    // Act
    CategoryDtoResponse response = categoryService.updateCategory(categoryUuid, categoryDtoRequest);

    // Assert
    assertThat(response).isEqualTo(categoryDtoResponse);

    then(categoryRepository).should(times(1)).findById(any(UUID.class));
    then(categoryRepository).should(times(1)).existsById(any(UUID.class));
    then(slugify).should(times(1)).slugify(anyString());
    then(categoryRepository).should(times(1)).save(any(Category.class));
    then(categoryMapper).should(times(1)).mapToDto(any(Category.class));
  }

  @Test
  void testDeleteCategoryWhenCategoryDoesNotExistThenThrowNotFoundException() {
    // Arrange
    given(categoryRepository.existsById(any(UUID.class))).willReturn(false);

    // Act & Assert
    assertThatThrownBy(() -> categoryService.deleteCategory(categoryUuid))
        .isInstanceOf(NotFoundException.class);

    then(categoryRepository).should(times(1)).existsById(any(UUID.class));
  }

  @Test
  void testDeleteCategoryWhenCategoryIsSuccessfullyDeletedThenReturnVoid() {
    // Arrange
    given(categoryRepository.existsById(any(UUID.class))).willReturn(true);

    // Act
    categoryService.deleteCategory(categoryUuid);

    // Assert
    then(categoryRepository).should(times(1)).existsById(any(UUID.class));
    then(categoryRepository).should(times(1)).deleteById(any(UUID.class));
  }

  @Test
  void testGetCategoryWhenCategoryDoesNotExistThenThrowNotFoundException() {
    // Arrange
    given(categoryRepository.findCategoryBySlugName(anyString())).willReturn(Optional.empty());

    // Act & Assert
    assertThatThrownBy(() -> categoryService.getCategory("test-category"))
        .isInstanceOf(NotFoundException.class);

    then(categoryRepository).should(times(1)).findCategoryBySlugName(anyString());
  }

  @Test
  void testGetCategoryWhenCategoryIsSuccessfullyRetrievedThenReturnCategoryDtoResponse() {
    // Arrange
    given(categoryRepository.findCategoryBySlugName(anyString())).willReturn(Optional.of(category));
    given(categoryMapper.mapToDto(any(Category.class))).willReturn(categoryDtoResponse);

    // Act
    CategoryDtoResponse response = categoryService.getCategory("test-category");

    // Assert
    assertThat(response).isEqualTo(categoryDtoResponse);

    then(categoryRepository).should(times(1)).findCategoryBySlugName(anyString());
    then(categoryMapper).should(times(1)).mapToDto(any(Category.class));
  }

  @Test
  void testGetAllCategoriesWhenThereAreNoCategoriesThenReturnEmptyList() {
    // Arrange
    PageRequest pageRequest = PageRequest.of(0, 10);
    given(categoryRepository.findAll(any(PageRequest.class))).willReturn(Page.empty());

    // Act
    PageDto<CategoryDtoResponse> response = categoryService.getAllCategories(pageRequest);

    // Assert
    assertThat(response.getData()).isEmpty();
    assertThat(response.getPage()).isEqualTo(0);
    assertThat(response.getSize()).isEqualTo(10);
    assertThat(response.getTotalElements()).isEqualTo(0);
    assertThat(response.getTotalPages()).isEqualTo(0);

    then(categoryRepository).should(times(1)).findAll(any(PageRequest.class));
  }

  @Test
  void testGetAllCategoriesWhenThereAreCategoriesThenReturnListOfCategories() {
    // Arrange
    PageRequest pageRequest = PageRequest.of(0, 10);
    List<Category> categories = List.of(category);
    Page<Category> categoryPage = new PageImpl<>(categories, pageRequest, categories.size());
    given(categoryRepository.findAll(any(PageRequest.class))).willReturn(categoryPage);
    given(categoryMapper.mapToDto(any(Category.class))).willReturn(categoryDtoResponse);
    given(categoryRepository.count()).willReturn(1L);
    // Act
    PageDto<CategoryDtoResponse> response = categoryService.getAllCategories(pageRequest);

    // Assert
    assertThat(response.getData()).hasSize(1);
    assertThat(response.getPage()).isEqualTo(0);
    assertThat(response.getSize()).isEqualTo(10);
    assertThat(response.getTotalElements()).isEqualTo(1);
    assertThat(response.getTotalPages())
        .isEqualTo(response.getTotalElements() / response.getSize());
    then(categoryRepository).should(times(1)).findAll(any(PageRequest.class));
    then(categoryMapper).should(times(1)).mapToDto(any(Category.class));
  }
}
