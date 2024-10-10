package com.github.ngodat0103.yamp.productsvc.service;

import com.github.ngodat0103.yamp.productsvc.dto.PageDto;
import com.github.ngodat0103.yamp.productsvc.dto.product.ProductDtoRequest;
import com.github.ngodat0103.yamp.productsvc.dto.product.ProductDtoResponse;
import java.util.UUID;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
  ProductDtoResponse createProduct(ProductDtoRequest productDtoRequest);

  ProductDtoResponse updateProduct(UUID productUuid, ProductDtoRequest productDtoRequest);

  void deleteProduct(UUID productUuid);

  ProductDtoResponse getProduct(String productSlugName);

  ProductDtoResponse getProduct(UUID productUuid);

  PageDto<ProductDtoResponse> getProducts(PageRequest pageRequest);
  //  String generatePresignedUrl(UUID productId);

}
