package org.example.productsvc.dto.mapper;

import org.example.productsvc.dto.ProductDto;
import org.example.productsvc.persistence.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper (componentModel = "spring")
public interface ProductMapper  {

     Product toProduct(ProductDto productDto);


    ProductDto toProductDto(Product product);
}
