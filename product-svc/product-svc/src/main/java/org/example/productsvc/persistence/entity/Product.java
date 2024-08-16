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
public class Product  extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID productUuid;
    private String name;
    private String shortDescription;
    private String longDescription;



    private UUID categoryUuid;
    private UUID brandUuid;
    @OneToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "categoryUuid", insertable = false, updatable = false)
    private Category category;

    @OneToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "brandUuid", insertable = false, updatable = false )
    private Brand brand;


    @OneToMany(mappedBy = "product",fetch = FetchType.EAGER)
    private Set<ProductImage> productImageSet;
    @OneToMany(mappedBy = "product",fetch = FetchType.EAGER)
    private Set<ProductVariation> productVariationSet;

}
