package com.github.ngodat0103.yamp.productsvc.service;

import com.github.ngodat0103.yamp.productsvc.dto.ProductDto;

import java.util.UUID;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    ProductDto updateProduct(ProductDto productDto);
    void deleteProduct();
    ProductDto getProduct(String productName);
    ProductDto getProduct(UUID productUuid);
}
