package com.github.ngodat0103.yamp.productsvc.service;

import com.github.ngodat0103.yamp.productsvc.dto.PageDto;
import com.github.ngodat0103.yamp.productsvc.dto.product.ProductDtoRequest;
import com.github.ngodat0103.yamp.productsvc.dto.product.ProductDtoResponse;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface ProductService {
    ProductDtoResponse createProduct(ProductDtoRequest productDtoRequest);
    ProductDtoResponse updateProduct(UUID productUuid, ProductDtoRequest productDtoRequest);
    void deleteProduct(UUID productUuid);
    ProductDtoResponse getProduct(String productSlugName);
    ProductDtoResponse getProduct(UUID productUuid);
    PageDto<ProductDtoResponse> getProducts(PageRequest pageRequest);
}
