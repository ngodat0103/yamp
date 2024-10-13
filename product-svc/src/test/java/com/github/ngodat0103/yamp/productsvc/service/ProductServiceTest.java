package com.github.ngodat0103.yamp.productsvc.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import com.github.ngodat0103.yamp.productsvc.dto.PageDto;
import com.github.ngodat0103.yamp.productsvc.dto.mapper.ProductMapper;
import com.github.ngodat0103.yamp.productsvc.dto.product.ProductDtoRequest;
import com.github.ngodat0103.yamp.productsvc.dto.product.ProductDtoResponse;
import com.github.ngodat0103.yamp.productsvc.exception.ConflictException;
import com.github.ngodat0103.yamp.productsvc.persistence.entity.Category;
import com.github.ngodat0103.yamp.productsvc.persistence.entity.Product;
import com.github.ngodat0103.yamp.productsvc.persistence.repository.CategoryRepository;
import com.github.ngodat0103.yamp.productsvc.persistence.repository.ProductRepository;
import com.github.ngodat0103.yamp.productsvc.service.impl.ProductServiceImpl;
import com.github.slugify.Slugify;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
  @Mock private ProductRepository productRepository;
  @Mock private CategoryRepository categoryRepository;
  @Mock private ProductMapper productMapper;
  @Mock private Slugify slugify;
  @Mock private SecurityContext securityContext;
  @Mock private Authentication authentication;
  @InjectMocks private ProductServiceImpl productService;

  private ProductDtoRequest productDtoRequest;
  private Product product;
  private Category category;
  private UUID productUuid;
  private UUID categoryUuid;
  private UUID accountUuid;

  @BeforeEach
  void setUp() {
    productDtoRequest =
        ProductDtoRequest.builder()
            .name("Test Product")
            .description("Test Description")
            .categorySlug("test-category")
            .build();

    category = new Category();
    category.setSlugName("test-category");

    product = new Product();
    product.setName("Test Product");
    product.setDescription("Test Description");
    product.setCategory(category);
    product.setSlugName("test-product");

    productUuid = UUID.randomUUID();
    categoryUuid = UUID.randomUUID();
    accountUuid = UUID.randomUUID();

    // Mock the authentication context
    lenient().when(authentication.getName()).thenReturn(accountUuid.toString());
    lenient().when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
  }

  @Test
  void testCreateProductWhenProductIsCreatedThenReturnProductDtoResponse() {
    // given
    given(categoryRepository.findCategoryBySlugName(productDtoRequest.getCategorySlug()))
        .willReturn(Optional.of(category));
    given(productRepository.existsByName(productDtoRequest.getName())).willReturn(false);
    given(productRepository.existsBySlugName(slugify.slugify(productDtoRequest.getName())))
        .willReturn(false);
    given(productMapper.toEntity(productDtoRequest, accountUuid)).willReturn(product);
    given(productRepository.save(product)).willReturn(product);
    given(productMapper.toProductDto(product))
        .willReturn(ProductDtoResponse.builder().name("Test Product").build());

    // when
    ProductDtoResponse response = productService.createProduct(productDtoRequest);

    // then
    assertThat(response).isNotNull();
    then(productRepository).should().save(product);
  }

  @Test
  void testCreateProductWhenProductWithSameNameExistsThenThrowConflictException() {
    // given
    given(categoryRepository.findCategoryBySlugName(productDtoRequest.getCategorySlug()))
        .willReturn(Optional.of(category));
    given(productRepository.existsByName(productDtoRequest.getName())).willReturn(true);

    // when & then
    assertThatThrownBy(() -> productService.createProduct(productDtoRequest))
        .isInstanceOf(ConflictException.class);
  }

  @Test
  void testUpdateProductWhenProductIsUpdatedThenReturnProductDtoResponse() {
    // given
    given(productRepository.findById(productUuid)).willReturn(Optional.of(product));
    given(categoryRepository.findCategoryBySlugName(productDtoRequest.getCategorySlug()))
        .willReturn(Optional.of(category));
    given(productRepository.save(product)).willReturn(product);
    given(productMapper.toProductDto(product))
        .willReturn(ProductDtoResponse.builder().name("Test Product").build());

    // when
    ProductDtoResponse response = productService.updateProduct(productUuid, productDtoRequest);

    // then
    assertThat(response).isNotNull();
    then(productRepository).should().save(product);
  }

  @Test
  void testDeleteProductWhenProductIsDeletedThenVerifyDeleteMethodIsCalled() {
    // given
    given(productRepository.findById(productUuid)).willReturn(Optional.of(product));

    // when
    productService.deleteProduct(productUuid);

    // then
    then(productRepository).should().delete(product);
  }

  @Test
  void testGetProductWhenProductIsRetrievedBySlugNameThenReturnProductDtoResponse() {
    // given
    given(productRepository.findBySlugName(product.getSlugName())).willReturn(Optional.of(product));
    given(productMapper.toProductDto(product))
        .willReturn(ProductDtoResponse.builder().name("Test Product").build());

    // when
    ProductDtoResponse response = productService.getProduct(product.getSlugName());

    // then
    assertThat(response).isNotNull();
    then(productRepository).should().findBySlugName(product.getSlugName());
  }

  @Test
  void testGetProductsWhenProductsAreRetrievedThenReturnListOfProductDtoResponse() {
    // given
    PageRequest pageRequest = PageRequest.of(0, 10);
    Page<Product> productPage = new PageImpl<>(new ArrayList<>(Set.of(product)));
    given(productRepository.findAll(pageRequest)).willReturn(productPage);
    given(productMapper.toProductDto(product))
        .willReturn(ProductDtoResponse.builder().name("Test Product").build());
    given(productRepository.count()).willReturn(1L);

    // when
    PageDto<ProductDtoResponse> response = productService.getProducts(pageRequest);

    // then
    assertThat(response).isNotNull();
    assertThat(response.getTotalElements()).isEqualTo(1);
    then(productRepository).should().findAll(pageRequest);
  }
}
