package com.github.ngodat0103.yamp.productsvc.persistence.repository;

import com.github.ngodat0103.yamp.productsvc.persistence.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {


    Optional<Category> findCategoryByName(String categoryName);
}
