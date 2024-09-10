package com.github.ngodat0103.yamp.productsvc.service;


import com.github.ngodat0103.yamp.productsvc.ProductSvcApplication;
import com.github.ngodat0103.yamp.productsvc.dto.CategoryDto;
import com.github.ngodat0103.yamp.productsvc.dto.mapper.CategoryMapperImpl;
import com.github.ngodat0103.yamp.productsvc.exception.ConflictException;
import com.github.ngodat0103.yamp.productsvc.persistence.entity.Category;
import com.github.ngodat0103.yamp.productsvc.persistence.repository.CategoryRepository;
import com.github.ngodat0103.yamp.productsvc.service.impl.CategoryServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.UUID;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class CategoryServiceTest {
    private CategoryRepository categoryRepository;
    private CategoryService categoryService;
    private static final String accountUuid = "24df6a88-85cf-44e2-89a1-f32588c708e7";

    private CategoryDto categoryDto;

    @BeforeEach
    public void setUp(){
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryService= new CategoryServiceImpl(categoryRepository,new CategoryMapperImpl());
        categoryDto =  CategoryDto.builder()
                .name("Giay dep")
                .parentCategoryUuid(null)
                .build();
    }

    @Test
    @DisplayName("Test create category")
    @WithMockUser(username = accountUuid, roles = "ADMIN")
    void givenNotConflict_whenCreateCategory_thenReturnCategoryDto(){
        Category category = new Category(categoryDto, UUID.fromString(accountUuid));
        given(categoryRepository.existsByName(categoryDto.getName())).willReturn(false);
        given(categoryRepository.save(any())).willReturn(category);
        CategoryDto result = categoryService.createCategory(categoryDto);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getName()).isEqualTo(categoryDto.getName());
    }

    @Test
    @DisplayName("Test create category given conflict")
    @WithMockUser(username = accountUuid, roles = "ADMIN")
    void givenConflict_whenCreateCategory_thenThrowException(){


        given(categoryRepository.existsByName(categoryDto.getName())).willReturn(true);
        Assertions.assertThatThrownBy(()-> categoryService.createCategory(categoryDto))
                .isInstanceOf(ConflictException.class)
                .hasMessage("Category name is already existed");
    }

}
