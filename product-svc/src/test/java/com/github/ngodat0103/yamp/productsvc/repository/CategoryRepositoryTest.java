package com.github.ngodat0103.yamp.productsvc.repository;


import com.github.ngodat0103.yamp.productsvc.persistence.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("unit-test")
@Disabled("Unit test still in progress")
public class CategoryRepositoryTest {
    private CategoryRepository categoryRepository;

    @Test
    public void givenSlugName_whenFindBySlugName_thenReturnNotFound() {
        Assertions.assertNull(categoryRepository.findCategoryBySlugName("slugName"));
    }
}
