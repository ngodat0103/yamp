package com.github.ngodat0103.yamp.productsvc.service.impl;

import com.github.ngodat0103.yamp.productsvc.dto.ProductDto;
import com.github.ngodat0103.yamp.productsvc.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {


    @Override
    public ProductDto createProduct(ProductDto productDto, UUID createdBy) {
        return null;
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, UUID updateBy) {
        return null;
    }

    @Override
    public void deleteProduct() {

    }

    @Override
    public ProductDto getProduct(String productName) {
        return null;
    }

    @Override
    public ProductDto getProduct(UUID productUuid) {
        return null;
    }
}
