package com.github.ngodat0103.yamp.productsvc.dto.mapper;

import com.github.ngodat0103.yamp.productsvc.dto.product.ProductDtoRequest;
import com.github.ngodat0103.yamp.productsvc.dto.product.ProductDtoResponse;
import com.github.ngodat0103.yamp.productsvc.persistence.entity.Product;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface ProductMapper {
  default Product toEntity(ProductDtoRequest productDtoRequest, UUID createBy) {
    Product product = new Product(createBy);
    product.setName(productDtoRequest.getName());
    product.setDescription(productDtoRequest.getDescription());
    return product;
  }

  @Mapping(target = "categorySlug", source = "category.slugName")
  ProductDtoResponse toProductDto(Product product);
}
