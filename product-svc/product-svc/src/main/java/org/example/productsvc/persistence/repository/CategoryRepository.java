package org.example.productsvc.persistence.repository;

import org.example.productsvc.persistence.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
