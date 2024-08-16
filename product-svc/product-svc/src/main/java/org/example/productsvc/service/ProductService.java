package org.example.productsvc.service;

import org.example.productsvc.dto.ProductDto;

import java.util.UUID;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    ProductDto updateProduct(ProductDto productDto);
    void deleteProduct();
    ProductDto getProduct(String productName);
    ProductDto getProduct(UUID productUuid);
}
