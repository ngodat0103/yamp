package com.github.ngodat0103.yamp.productsvc.repository;

import com.github.ngodat0103.yamp.productsvc.ProductSvcApplication;
import com.github.ngodat0103.yamp.productsvc.dto.CategoryDto;
import com.github.ngodat0103.yamp.productsvc.persistence.entity.Category;
import com.github.ngodat0103.yamp.productsvc.persistence.repository.CategoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;


@DataJpaTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;


    private  CategoryDto categoryDto;

    private final UUID createBy = UUID.randomUUID();



    @BeforeEach
    public void setUp(){
        categoryDto = CategoryDto.builder()
                .name("Giay dep")
                .parentCategoryUuid(null)
                .build();
    }

    @DisplayName("Test save category")
    @Test
    public void givenCategoryObject_whenSave_thenReturnSavedEmployee(){
        Category category = new Category(categoryDto,createBy);
        Category savedCategory = categoryRepository.save(category);
        Assertions.assertThat(savedCategory).isNotNull();
        Assertions.assertThat(savedCategory.getUuid()).isNotNull();
        Assertions.assertThatObject(savedCategory.getUuid()).isNotNull();
        Assertions.assertThat(savedCategory.getName()).isEqualTo(categoryDto.getName());
    }

    @DisplayName("Test get all categories")
    @Test
    public void givenCategoryList_whenFindAll_thenReturnAllCategories(){
        Category category1 = new Category(categoryDto,createBy);
        categoryDto.setName("Giay dep 2");
        Category category2 = new Category(categoryDto,createBy);
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        List<Category> categories = categoryRepository.findAll();
        Assertions.assertThat(categories).isNotNull();
        Assertions.assertThat(categories.size()).isEqualTo(2);
        Assertions.assertThat(categories.get(0).getName()).isEqualTo(category1.getName());
        Assertions.assertThat(categories.get(1).getName()).isEqualTo(category2.getName());
    }

    @DisplayName("Test remove category")
    @Test
    public void givenCategoryUuid_whenDelete_thenRemoveCategory(){
        var category = new Category(categoryDto,createBy);
        var savedCategory = categoryRepository.save(category);
        UUID categoryUuid = savedCategory.getUuid();
        categoryRepository.deleteById(categoryUuid);
        var  categoryOptional = categoryRepository.findById(categoryUuid);
        Assertions.assertThat(categoryOptional).isEmpty() ;
    }


    @DisplayName("Test update category")
    @Test
    public void givenCategoryDto_whenUpdate_thenUpdateCategory(){
        var category = new Category(categoryDto,createBy);
        categoryRepository.save(category);

        var savedCategory = categoryRepository.findById(category.getUuid()).orElse(null);
        assert savedCategory != null;
        savedCategory.setName("Giay dep 2");
        var updateCategory =   categoryRepository.save(savedCategory);


        Assertions.assertThat(updateCategory).isNotNull();
        Assertions.assertThat(updateCategory.getName()).isEqualTo("Giay dep 2");


    }
}
