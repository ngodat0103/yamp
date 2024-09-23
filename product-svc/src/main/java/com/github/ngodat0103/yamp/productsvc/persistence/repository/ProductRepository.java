package com.github.ngodat0103.yamp.productsvc.persistence.repository;

import com.github.ngodat0103.yamp.productsvc.persistence.entity.Product;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
  Boolean existsByName(String name);

  Optional<Product> findBySlugName(String slugName);

  Boolean existsBySlugName(String slugName);
}
