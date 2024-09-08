package com.github.ngodat0103.yamp.productsvc.service;


import com.github.ngodat0103.yamp.productsvc.CustomerTestWithMockUser;
import com.github.ngodat0103.yamp.productsvc.dto.CategoryDto;
import com.github.ngodat0103.yamp.productsvc.dto.PageDto;
import com.github.ngodat0103.yamp.productsvc.dto.mapper.CategoryMapper;
import com.github.ngodat0103.yamp.productsvc.dto.mapper.CategoryMapperImpl;
import com.github.ngodat0103.yamp.productsvc.exception.ConflictException;
import com.github.ngodat0103.yamp.productsvc.exception.NotFoundException;
import com.github.ngodat0103.yamp.productsvc.persistence.entity.Category;
import com.github.ngodat0103.yamp.productsvc.persistence.repository.CategoryRepository;
import com.github.ngodat0103.yamp.productsvc.service.impl.CategoryServiceImpl;
import com.github.slugify.Slugify;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Locale;
import java.util.UUID;

import static org.mockito.BDDMockito.*;
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("unit-test")
public class CategoryServiceTest {
    private  CategoryService categoryService;
    private  CategoryRepository categoryRepository;
    private CategoryDto categoryDto;
    private Category category;

    @BeforeEach
    void setUp() {
        this.categoryRepository = Mockito.mock(CategoryRepository.class);
        CategoryMapper categoryMapper = new CategoryMapperImpl();
        Locale locale = new Locale("vi", "VN");
        Slugify slugify = Slugify.builder()
                .locale(locale)
                .lowerCase(true)
                .build();
        this.categoryService = new CategoryServiceImpl(categoryRepository, categoryMapper, slugify);
        Category category = new Category();
        category.setUuid(UUID.randomUUID());
        category.setName("Category test");
        category.setSlugName("category-test");
        category.setThumbnailUrl("https://example.com/thumbnail.jpg");
        category.setParentCategoryUuid(UUID.randomUUID());
        this.categoryDto = categoryMapper.mapToDto(category);
        this.category = category;
    }

    @Test
    @DisplayName("Call service but not contain Authentication object")
    public void givenNoAuthentication_whenCreateCategory_thenThrowIllegalArgumentException() {
       Exception exceptionForCreate =    Assertions.assertThrows(IllegalArgumentException.class, () -> {
            categoryService.createCategory(categoryDto);
        });
       Exception exceptionForUpdate =    Assertions.assertThrows(IllegalArgumentException.class, () -> {
            categoryService.updateCategory(UUID.randomUUID(),categoryDto);
        });
       Assertions.assertAll(
               () -> Assertions.assertEquals("Authentication object is required", exceptionForCreate.getMessage()),
               () -> Assertions.assertEquals("Authentication object is required", exceptionForUpdate.getMessage())
       );
    }
    @CustomerTestWithMockUser
    @DisplayName("Given a CategoryDto, when creating a category, then throw ConflictException if the category name already exists")
    public void givenCategoryDto_whenCreateCategory_thenThrowConflictException() {
        given(categoryRepository.existsByName(categoryDto.getName())).willReturn(true);
       Exception exception =  Assertions.assertThrows(ConflictException.class, () -> {
            categoryService.createCategory(categoryDto);
        });
        Assertions.assertEquals("Category name is already existed", exception.getMessage());
        then(categoryRepository).should().existsByName(categoryDto.getName());
    }
    @CustomerTestWithMockUser
    @DisplayName("Given a CategoryDto, when creating a category, then throw NotFoundException if the parent category does not exist")
    public void givenCategoryDto_whenCreateCategory_thenThrowNotFoundException() {
        given(categoryRepository.existsByName(categoryDto.getName())).willReturn(false);
        given(categoryRepository.existsByParentCategoryUuid(categoryDto.getParentCategoryUuid())).willReturn(false);
      Exception exception =   Assertions.assertThrows(NotFoundException.class,() -> {
            categoryService.createCategory(categoryDto);
        });
        Assertions.assertEquals("Category with parentCategoryUuid: " + categoryDto.getParentCategoryUuid() + " not found", exception.getMessage());
        then(categoryRepository).should().existsByName(categoryDto.getName());
        then(categoryRepository).should().existsByParentCategoryUuid(categoryDto.getParentCategoryUuid());
    }

    @CustomerTestWithMockUser
    @DisplayName("Given a CategoryDto, when creating a category, then return a CategoryDto")
    public void givenCategoryDto_whenCreateCategory_thenReturnCategoryDto() {
        given(categoryRepository.existsByName(categoryDto.getName())).willReturn(false);
        given(categoryRepository.existsByParentCategoryUuid(categoryDto.getParentCategoryUuid())).willReturn(true);
        given(categoryRepository.save(any(Category.class))).willReturn(category);
        CategoryDto categoryDto = categoryService.createCategory(this.categoryDto);
        Assertions.assertNotNull(categoryDto);
        then(categoryRepository).should().existsByName(categoryDto.getName());
        then(categoryRepository).should().existsByParentCategoryUuid(categoryDto.getParentCategoryUuid());
        then(categoryRepository).should().save(any(Category.class));
    }

    @CustomerTestWithMockUser
    @DisplayName("Given a CategoryDto, when updating a category, then throw NotFoundException if the category does not exist")
    public void givenCategoryDto_whenUpdateCategory_thenThrowNotFoundException() {
        given(categoryRepository.findById(category.getUuid())).willReturn(java.util.Optional.empty());
        Exception exception = Assertions.assertThrows(NotFoundException.class, () -> {
            categoryService.updateCategory(category.getUuid(), categoryDto);
        });
        Assertions.assertEquals("Category not found", exception.getMessage());
    }

    @CustomerTestWithMockUser
    @DisplayName("Given a CategoryDto, when updating a category, then throw NotFoundException if the parent category does not exist")
    public void givenCategoryDto_whenUpdateCategory_thenThrowNotFoundExceptionIfParentNotExists() {
        given(categoryRepository.findById(category.getUuid())).willReturn(java.util.Optional.of(category));
        given(categoryRepository.existsById(categoryDto.getParentCategoryUuid())).willReturn(false);
        Exception exception = Assertions.assertThrows(NotFoundException.class, () -> {
            categoryService.updateCategory(category.getUuid(), categoryDto);
        });
        Assertions.assertEquals("Parent category not found", exception.getMessage());
    }

    @CustomerTestWithMockUser
    @DisplayName("Given a CategoryDto, when updating a category, then return a CategoryDto")
    public void givenCategoryDto_whenUpdateCategory_thenReturnCategoryDto() {
        given(categoryRepository.findById(category.getUuid())).willReturn(java.util.Optional.of(category));
        given(categoryRepository.existsById(categoryDto.getParentCategoryUuid())).willReturn(true);
        given(categoryRepository.save(any(Category.class))).willReturn(category);
        CategoryDto categoryDto = categoryService.updateCategory(category.getUuid(), this.categoryDto);
        Assertions.assertNotNull(categoryDto);
        then(categoryRepository).should().findById(category.getUuid());
        then(categoryRepository).should().existsById(categoryDto.getParentCategoryUuid());
        then(categoryRepository).should().save(any(Category.class));
    }


    @Test
    @DisplayName("Given a category UUID, when deleting a category, then throw NotFoundException if the category does not exist")
    public void givenCategoryUuid_whenDeleteCategory_thenThrowNotFoundException() {
        given(categoryRepository.existsById(category.getUuid())).willReturn(false);
        Exception exception = Assertions.assertThrows(NotFoundException.class, () -> {
            categoryService.deleteCategory(category.getUuid());
        });
        Assertions.assertEquals("Category not found", exception.getMessage());
    }

    @Test
    @DisplayName("Given a category UUID, when deleting a category, then delete the category")
    public void givenCategoryUuid_whenDeleteCategory_thenDeleteCategory() {
        given(categoryRepository.existsById(category.getUuid())).willReturn(true);
        categoryService.deleteCategory(category.getUuid());
        then(categoryRepository).should().deleteById(category.getUuid());
    }

    @Test
    @DisplayName("Given categorySlug, when get category by slugName, then throw NotFoundException if the category does not exist")
    public void givenCategorySlug_whenGetCategoryBySlug_thenThrowNotFoundException() {
        given(categoryRepository.findCategoryBySlugName(category.getName())).willReturn(java.util.Optional.empty());
        Exception exception = Assertions.assertThrows(NotFoundException.class, () -> {
            categoryService.getCategory(category.getName());
        });
        Assertions.assertEquals("Category with slugName " + category.getName() + " not found", exception.getMessage());
    }


    @Test
    @DisplayName("Given slugName, when get category by slugName, then return a CategoryDto")
    public void givenCategoryUuid_whenGetCategoryBySlug_thenReturnCategoryDto() {
        given(categoryRepository.findCategoryBySlugName(categoryDto.getSlugName())).willReturn(java.util.Optional.of(category));
        CategoryDto dtpResponse = categoryService.getCategory(categoryDto.getSlugName());
        Assertions.assertNotNull(dtpResponse);
        Assertions.assertEquals(category.getName(), dtpResponse.getName());
        Assertions.assertEquals(category.getUuid(), dtpResponse.getUuid());
        Assertions.assertEquals(category.getParentCategoryUuid(), dtpResponse.getParentCategoryUuid());
        Assertions.assertEquals(category.getThumbnailUrl(), dtpResponse.getThumbnailUrl());
        Assertions.assertEquals(category.getSlugName(), dtpResponse.getSlugName());
        then(categoryRepository).should().findCategoryBySlugName(categoryDto.getSlugName());
    }

    @Test
    @DisplayName("Given category UUID, when get category by UUID, then throw NotFoundException if the category does not exist")
    public void givenCategoryUuid_whenGetCategoryByUuid_thenThrowNotFoundException() {
        given(categoryRepository.findById(category.getUuid())).willReturn(java.util.Optional.empty());
        Exception exception = Assertions.assertThrows(NotFoundException.class, () -> {
            categoryService.getCategory(category.getUuid());
        });
        Assertions.assertEquals("Category not found", exception.getMessage());
    }

    @Test
    @DisplayName("Given category UUID, when get category by UUID, then return a CategoryDto")
    public void givenCategoryUuid_whenGetCategoryByUuid_thenReturnCategoryDto() {
        given(categoryRepository.findById(category.getUuid())).willReturn(java.util.Optional.of(category));
        CategoryDto dtpResponse = categoryService.getCategory(category.getUuid());
        Assertions.assertNotNull(dtpResponse);
        Assertions.assertEquals(category.getName(), dtpResponse.getName());
        Assertions.assertEquals(category.getUuid(), dtpResponse.getUuid());
        Assertions.assertEquals(category.getParentCategoryUuid(), dtpResponse.getParentCategoryUuid());
        Assertions.assertEquals(category.getThumbnailUrl(), dtpResponse.getThumbnailUrl());
        Assertions.assertEquals(category.getSlugName(), dtpResponse.getSlugName());
        then(categoryRepository).should().findById(category.getUuid());
    }

    @Test
    @DisplayName("Given nothing, when get all categories, then return a list of CategoryDto")
    public void givenNothing_whenGetAllCategories_thenReturnListOfCategoryDto() {
        PageRequest pageRequest = PageRequest.of(0, 100);
        PageImpl<Category> pageImpl = new PageImpl<>(java.util.List.of(category), pageRequest, 1);

        given(categoryRepository.findAll(pageRequest)).willReturn(pageImpl);
        given(categoryRepository.count()).willReturn(1L);

        PageDto<CategoryDto> pageDto = categoryService.getAllCategories(PageRequest.of(0, 100));
        Assertions.assertNotNull(pageDto);
        Assertions.assertEquals(pageRequest.getPageNumber(), pageDto.getPage());
        Assertions.assertEquals(pageRequest.getPageSize(), pageDto.getSize());
        Assertions.assertEquals(pageImpl.getTotalElements(), pageDto.getTotalElements());

        Assertions.assertEquals(1, pageDto.getData().size());


        pageDto.getData().forEach(categoryDto -> {
            Assertions.assertEquals(category.getName(), categoryDto.getName());
            Assertions.assertEquals(category.getUuid(), categoryDto.getUuid());
            Assertions.assertEquals(category.getParentCategoryUuid(), categoryDto.getParentCategoryUuid());
            Assertions.assertEquals(category.getThumbnailUrl(), categoryDto.getThumbnailUrl());
            Assertions.assertEquals(category.getSlugName(), categoryDto.getSlugName());
        });
        then(categoryRepository).should().findAll(pageRequest);
        then(categoryRepository).should().count();
    }





}
