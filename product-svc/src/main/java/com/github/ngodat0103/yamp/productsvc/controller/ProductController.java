package com.github.ngodat0103.yamp.productsvc.controller;

import com.github.ngodat0103.yamp.productsvc.dto.PageDto;
import com.github.ngodat0103.yamp.productsvc.dto.product.ProductDtoRequest;
import com.github.ngodat0103.yamp.productsvc.dto.product.ProductDtoResponse;
import com.github.ngodat0103.yamp.productsvc.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/** The type Product controller. */
@RestController
@RequestMapping(path = "/products")
public class ProductController {
  private final ProductService productService;

  /**
   * Instantiates a new Product controller.
   *
   * @param productService the product service
   */
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  /**
   * Gets products.
   *
   * @param page the page
   * @param size the size
   * @return the products
   */
  @GetMapping(path = "/all")
  public PageDto<ProductDtoResponse> getProducts(
      @RequestParam(required = false, defaultValue = "0") int page,
      @RequestParam(required = false, defaultValue = "100") int size) {
    return productService.getProducts(PageRequest.of(page, size));
  }

  /**
   * Gets product.
   *
   * @param slugName the slug name
   * @return the product
   */
  @GetMapping(path = "/{slugName}")
  public ProductDtoResponse getProduct(@PathVariable String slugName) {
    return productService.getProduct(slugName);
  }

  /**
   * Gets product.
   *
   * @param uuid the uuid
   * @return the product
   */
  @GetMapping
  public ProductDtoResponse getProduct(@RequestParam UUID uuid) {
    return productService.getProduct(uuid);
  }

  /**
   * Create product product dto response.
   *
   * @param productDtoRequest the product dto request
   * @return the product dto response
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasRole('ADMIN')")
  @SecurityRequirement(name = "oauth2")
  public ProductDtoResponse createProduct(@RequestBody @Valid ProductDtoRequest productDtoRequest) {
    return productService.createProduct(productDtoRequest);
  }

  /**
   * Update product product dto response.
   *
   * @param productUuid the product uuid
   * @param productDtoRequest the product dto request
   * @return the product dto response
   */
  @PutMapping(path = "/{productUuid}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  @PreAuthorize("hasRole('ADMIN')")
  @SecurityRequirement(name = "oauth2")
  public ProductDtoResponse updateProduct(
      @PathVariable UUID productUuid, @RequestBody @Valid ProductDtoRequest productDtoRequest) {
    return productService.updateProduct(productUuid, productDtoRequest);
  }

  /**
   * Delete product.
   *
   * @param productUuid the product uuid
   */
  @DeleteMapping(path = "/{productUuid}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize("hasRole('ADMIN')")
  @SecurityRequirement(name = "oauth2")
  public void deleteProduct(@PathVariable UUID productUuid) {
    productService.deleteProduct(productUuid);
  }
}
