package com.github.ngodat0103.yamp.productsvc.persistence.repository;

import com.github.ngodat0103.yamp.productsvc.persistence.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface CategoryRepository extends JpaRepository<ProductCategory, UUID> {
}
