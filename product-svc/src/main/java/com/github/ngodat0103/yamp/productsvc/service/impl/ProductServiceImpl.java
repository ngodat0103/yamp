package com.github.ngodat0103.yamp.productsvc.service.impl;

import static com.github.ngodat0103.yamp.productsvc.Util.*;

import com.github.ngodat0103.yamp.productsvc.dto.PageDto;
import com.github.ngodat0103.yamp.productsvc.dto.mapper.ProductMapper;
import com.github.ngodat0103.yamp.productsvc.dto.product.ProductDtoRequest;
import com.github.ngodat0103.yamp.productsvc.dto.product.ProductDtoResponse;
import com.github.ngodat0103.yamp.productsvc.exception.NotFoundException;
import com.github.ngodat0103.yamp.productsvc.persistence.entity.Category;
import com.github.ngodat0103.yamp.productsvc.persistence.entity.Product;
import com.github.ngodat0103.yamp.productsvc.persistence.repository.CategoryRepository;
import com.github.ngodat0103.yamp.productsvc.persistence.repository.ProductRepository;
import com.github.ngodat0103.yamp.productsvc.service.ProductService;
import com.github.slugify.Slugify;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Service;

/** The type Product service. */
@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;
  private final Slugify slugify;
  private final UriTemplate uriTemplate = UriTemplate.of("/api/v1/product/products/{placeholder}");
  private final UriTemplate productQueryUriTemplate =
      UriTemplate.of("/api/v1/product/products{?uuid}");
  private final UriTemplate uriTemplateCategory =
      UriTemplate.of("/api/v1/category/categories/{placeholder}");

  /** The Product mapper. */
  ProductMapper productMapper;

  @Override
  public ProductDtoResponse createProduct(ProductDtoRequest productDtoRequest) {
    Category category =
        categoryRepository
            .findCategoryBySlugName(productDtoRequest.getCategorySlug())
            .orElseThrow(
                notFoundExceptionSupplier(
                    log, "Category", "slugName", productDtoRequest.getCategorySlug()));
    if (productRepository.existsByName(productDtoRequest.getName())) {
      throwConflictException(log, "Product", "name", productDtoRequest.getName());
    }

    if (productRepository.existsBySlugName(slugify.slugify(productDtoRequest.getName()))) {
      throwConflictException(
          log, "Product", "slugName", slugify.slugify(productDtoRequest.getName()));
    }

    Product product = productMapper.toEntity(productDtoRequest, getAccountUuidFromAuthentication());
    product.setCategory(category);
    product.setSlugName(slugify.slugify(productDtoRequest.getName()));
    product = productRepository.save(product);
    return productMapper.toProductDto(product);
  }

  @Override
  public ProductDtoResponse updateProduct(UUID productUuid, ProductDtoRequest productDtoRequest) {
    Product currentProduct =
        productRepository
            .findById(productUuid)
            .orElseThrow(notFoundExceptionSupplier(log, "Product", "uuid", productUuid));
    Category newCategory =
        categoryRepository
            .findCategoryBySlugName(productDtoRequest.getCategorySlug())
            .orElseThrow(
                notFoundExceptionSupplier(
                    log, "Category", "slugName", productDtoRequest.getCategorySlug()));

    currentProduct.setCategory(newCategory);
    currentProduct.setName(productDtoRequest.getName());
    currentProduct.setDescription(productDtoRequest.getDescription());
    currentProduct.setSlugName(slugify.slugify(productDtoRequest.getName()));

    currentProduct.setLastModifiedBy(getAccountUuidFromAuthentication());
    currentProduct.setLastModifiedAt(LocalDateTime.now());
    currentProduct = productRepository.save(currentProduct);

    return productMapper.toProductDto(currentProduct);
  }

  @Override
  public void deleteProduct(UUID productUuid) {
    Product product =
        productRepository
            .findById(productUuid)
            .orElseThrow(notFoundExceptionSupplier(log, "Product", "uuid", productUuid));
    productRepository.delete(product);
  }

  @Override
  public ProductDtoResponse getProduct(String productSlugName) {
    Product product =
        productRepository
            .findBySlugName(productSlugName)
            .orElseThrow(
                () -> {
                  String message = "Product with slugName: " + productSlugName + " not found";
                  log.debug(message);
                  return new NotFoundException(message);
                });

    ProductDtoResponse productDtoResponse = productMapper.toProductDto(product);
    addLinks(productDtoResponse, productSlugName);
    return productDtoResponse;
  }

  @Override
  public ProductDtoResponse getProduct(UUID productUuid) {
    Product product =
        productRepository
            .findById(productUuid)
            .orElseThrow(notFoundExceptionSupplier(log, "Product", "uuid", productUuid));
    ProductDtoResponse productDtoResponse = productMapper.toProductDto(product);
    addLinks(productDtoResponse, product.getSlugName());
    return productDtoResponse;
  }

  @Override
  public PageDto<ProductDtoResponse> getProducts(PageRequest pageRequest) {
    Set<ProductDtoResponse> productDtoResponses =
        productRepository.findAll(pageRequest).stream()
            .map(
                account -> {
                  ProductDtoResponse productDtoResponse = productMapper.toProductDto(account);
                  addLinks(productDtoResponse, account.getSlugName());
                  return productDtoResponse;
                })
            .collect(Collectors.toUnmodifiableSet());

    int totalElements = (int) productRepository.count();
    return PageDto.<ProductDtoResponse>builder()
        .page(pageRequest.getPageNumber())
        .size(pageRequest.getPageSize())
        .totalElements(totalElements)
        .totalPages((totalElements / pageRequest.getPageSize()))
        .data(productDtoResponses)
        .build();
  }

  private void addLinks(ProductDtoResponse productDtoResponse, String slugName) {
    Link updateLink =
        Link.of(uriTemplate.expand(productDtoResponse.getUuid()).toString(), "update")
            .withTitle("Update product")
            .withType("application/json");
    Link deleteLink =
        Link.of(uriTemplate.expand(productDtoResponse.getUuid()).toString(), "delete")
            .withTitle("Delete product")
            .withType("application/json");
    Link slugLink =
        Link.of(uriTemplate.expand(slugName).toString(), "slugName")
            .withTitle("Get product by slugName")
            .withType("application/json");
    Link categoryLink =
        Link.of("/api/v1/category/categories/" + productDtoResponse.getCategorySlug(), "category")
            .withTitle("Get category by slugName")
            .withType("application/json");

    Link uuidLink =
        Link.of(productQueryUriTemplate.expand(productDtoResponse.getUuid()).toString(), "uuid")
            .withTitle("Get product by uuid")
            .withType("application/json");
    productDtoResponse.add(uuidLink, slugLink, updateLink, deleteLink, categoryLink);
  }

  //  @Override
  //  public String generatePresignedUrl(UUID productId) {
  //    S3Presigner presigner = S3Presigner.builder()
  //            .region(Region.US_EAST_1) // Set your region
  //            .credentialsProvider(DefaultCredentialsProvider.create())
  //            .build();
  //
  //    PutObjectRequest putObjectRequest = PutObjectRequest.builder()
  //            .bucket("your-bucket-name")
  //            .key("images/" + productId)
  //            .build();
  //
  //    PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
  //            .signatureDuration(Duration.ofMinutes(15))
  //            .putObjectRequest(putObjectRequest)
  //            .build();
  //
  //    URL presignedUrl = presigner.presignPutObject(presignRequest).url();
  //    presigner.close();
  //
  //    // Save the imageUrl to the Product entity
  //    String imageUrl = "https://your-bucket-name.s3.amazonaws.com/product/image/" + productId;
  //    updateProductImageUrl(productId, imageUrl);
  //
  //    return presignedUrl.toString();
  //  }
  //
  //  private void updateProductImageUrl(UUID productId, String imageUrl) {
  //    Optional<Product> productOptional = productRepository.findById(productId);
  //    if (productOptional.isPresent()) {
  //      Product product = productOptional.get();
  //      product.setImageUrl(imageUrl);
  //      productRepository.save(product);
  //    } else {
  //      throw new RuntimeException("Product not found with id: " + productId);
  //    }
  //  }

}
