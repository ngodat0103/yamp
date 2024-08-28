package com.github.ngodat0103.yamp.productsvc.persistence.entity;

import com.github.ngodat0103.yamp.productsvc.dto.CategoryDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentCategoryUuid", insertable = false, updatable = false)
    private Category parentCategory;
    @OneToMany(mappedBy = "parentCategory")
    private Set<Category> subCategories;



    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "uuid")
    private Set<Product> productSet;



    public Category(CategoryDto categoryDto , UUID createdBy) {
        super(createdBy);
        this.name = categoryDto.getName();
        this.parentCategoryUuid = categoryDto.getParentCategoryUuid();
    }




    public void updateCategory(CategoryDto categoryDto, UUID updatedBy) {
        assert categoryDto!=null;
        assert updatedBy!=null;
        this.name = categoryDto.getName();
        this.parentCategoryUuid = categoryDto.getParentCategoryUuid();
        super.setLastModifiedBy(updatedBy);
        super.setLastModifiedAt(LocalDateTime.now());
    }

}
