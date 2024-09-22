package com.github.ngodat0103.yamp.productsvc.persistence.repository;

import com.github.ngodat0103.yamp.productsvc.persistence.entity.Category;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
  Boolean existsByName(String categoryName);

  Boolean existsByParentCategoryUuid(UUID parentCategoryUuid);

  Optional<Category> findCategoryBySlugName(String slugName);
}
