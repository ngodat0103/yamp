package com.github.ngodat0103.yamp.productsvc.dto.mapper;

import com.github.ngodat0103.yamp.productsvc.persistence.entity.Product;
import com.github.ngodat0103.yamp.productsvc.dto.ProductDto;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public interface ProductMapper  {
     Product toProduct(ProductDto productDto);
    ProductDto toProductDto(Product product);
}
