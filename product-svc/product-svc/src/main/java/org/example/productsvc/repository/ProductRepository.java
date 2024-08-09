package org.example.productsvc.repository;

import org.example.productsvc.persistence.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Product findByProductUuid(UUID productUuid);
}
