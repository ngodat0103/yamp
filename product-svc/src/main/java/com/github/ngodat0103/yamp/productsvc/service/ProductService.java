package com.github.ngodat0103.yamp.productsvc.service;

import com.github.ngodat0103.yamp.productsvc.dto.product.ProductDto;

import java.util.Set;
import java.util.UUID;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    ProductDto updateProduct(UUID productUuid, ProductDto productDto);
    void deleteProduct(UUID productUuid);
    ProductDto getProduct(String productSlugName);
    ProductDto getProduct(UUID productUuid);
    Set<ProductDto> getProducts();

}
