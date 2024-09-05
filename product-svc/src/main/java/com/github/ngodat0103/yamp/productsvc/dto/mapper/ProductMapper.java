package com.github.ngodat0103.yamp.productsvc.dto.mapper;

import com.github.ngodat0103.yamp.productsvc.dto.product.ProductDto;
import com.github.ngodat0103.yamp.productsvc.persistence.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper (componentModel = "spring",unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface ProductMapper  {
    default Product toEntity(ProductDto productDto, UUID createBy){
        Product product = new Product(createBy);
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        return product;
    }

    @Mapping(target = "categorySlug", source = "category.slugName")
    ProductDto toProductDto(Product product);
}
