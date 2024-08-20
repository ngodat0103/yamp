package com.github.ngodat0103.yamp.productsvc.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Category extends BaseEntity{
    @Column(unique = true)
    private String name;
    private UUID parentCategoryUuid;
    @ManyToOne
    @JoinColumn(name = "parentCategoryUuid", insertable = false, updatable = false)
    private Category parentCategory;
    @OneToMany(mappedBy = "parentCategory")
    private Set<Category> subCategories;



    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "uuid")
    private Set<Product> productSet;

    public Category(UUID createdBy) {
        super(createdBy);
    }

}
