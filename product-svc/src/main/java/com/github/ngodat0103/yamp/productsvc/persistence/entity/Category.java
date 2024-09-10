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
    @Column(unique = true)
    private String slugName;

    private String thumbnailUrl;
    @Column(name = "parent_category_Uuid")
    private UUID parentCategoryUuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_Uuid", insertable = false, updatable = false)
    private Category parentCategory;
    @OneToMany(mappedBy = "parentCategory")
    private Set<Category> subCategories;



    @OneToMany(fetch = FetchType.EAGER,mappedBy = "category")
    private Set<Product> productSet;



    public Category(UUID createdBy) {
        super(createdBy);
    }



}
