package com.github.ngodat0103.yamp.productsvc.dto.mapper;

import com.github.ngodat0103.yamp.productsvc.dto.ProductDto;
import com.github.ngodat0103.yamp.productsvc.persistence.entity.Product;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring",unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface ProductMapper  {
     Product toProduct(ProductDto productDto);
    ProductDto toProductDto(Product product);
}
