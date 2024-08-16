package org.example.productsvc.persistence.entity;

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
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID categoryUuid;
    @Column(unique = true)
    private String name;



    private UUID parentCategoryUuid;
    @ManyToOne
    @JoinColumn(name = "parentCategoryUuid", insertable = false, updatable = false)
    private Category parentCategory;
    @OneToMany(mappedBy = "parentCategory")
    private Set<Category> subCategories;
    @OneToOne(mappedBy = "category")
    private Product product;

    public Category(String createdBy) {
        super(createdBy);
    }

}
