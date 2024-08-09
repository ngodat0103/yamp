package org.example.productsvc.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID categoryUuid;
    @Column(unique = true)
    private String name;



    private UUID parentCategoryUuid;
    @ManyToOne
    @JoinColumn(name = "parentCategoryUuid", insertable = false, updatable = false)
    private ProductCategory parentCategory;
    @OneToMany(mappedBy = "parentCategory")
    private Set<ProductCategory> subCategories;
    @OneToOne(mappedBy = "productCategory")
    private Product product;
}
