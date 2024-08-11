package org.example.productsvc.dto.mapper;

import org.example.productsvc.dto.ProductDto;
import org.example.productsvc.persistence.entity.Product;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public interface ProductMapper  {

     Product toProduct(ProductDto productDto);


    ProductDto toProductDto(Product product);
}
