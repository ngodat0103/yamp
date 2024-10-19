package com.github.ngodat0103.yamp.productsvc.controller;

import com.github.ngodat0103.yamp.productsvc.dto.PageDto;
import com.github.ngodat0103.yamp.productsvc.dto.product.ProductDtoRequest;
import com.github.ngodat0103.yamp.productsvc.dto.product.ProductDtoResponse;
import com.github.ngodat0103.yamp.productsvc.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/** The type Product controller. */
@RestController
@RequestMapping(path = "/products")
@Tag(name = "Product Management", description = "Operations related to product management")
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
  @Operation(
      summary = "Get all products",
      description = "Retrieve a paginated list of all products")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Successfully retrieved products"),
    @ApiResponse(responseCode = "400", description = "Invalid request parameters")
  })
  @GetMapping(path = "/all")
  public PageDto<ProductDtoResponse> getProducts(
      @Parameter(description = "Page number", example = "0")
          @RequestParam(required = false, defaultValue = "0")
          int page,
      @Parameter(description = "Page size", example = "100")
          @RequestParam(required = false, defaultValue = "100")
          int size) {
    return productService.getProducts(PageRequest.of(page, size));
  }

  /**
   * Gets product.
   *
   * @param slugName the slug name
   * @return the product
   */
  @Operation(
      summary = "Get product by slug name",
      description = "Retrieve a product by its slug name")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Successfully retrieved product"),
    @ApiResponse(responseCode = "404", description = "Product not found")
  })
  @GetMapping(path = "/{slugName}")
  public ProductDtoResponse getProduct(
      @Parameter(description = "Slug name of the product") @PathVariable String slugName) {
    return productService.getProduct(slugName);
  }

  /**
   * Gets product.
   *
   * @param uuid the uuid
   * @return the product
   */
  @Operation(summary = "Get product by UUID", description = "Retrieve a product by its UUID")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Successfully retrieved product"),
    @ApiResponse(responseCode = "404", description = "Product not found")
  })
  @GetMapping
  public ProductDtoResponse getProduct(
      @Parameter(description = "UUID of the product") @RequestParam UUID uuid) {
    return productService.getProduct(uuid);
  }

  /**
   * Create product product dto response.
   *
   * @param productDtoRequest the product dto request
   * @return the product dto response
   */
  @Operation(
      summary = "Create a new product",
      description = "Create a new product with the provided details")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Successfully created product"),
    @ApiResponse(responseCode = "400", description = "Invalid request body")
  })
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
  @Operation(
      summary = "Update an existing product",
      description = "Update an existing product with the provided details")
  @ApiResponses({
    @ApiResponse(responseCode = "202", description = "Successfully updated product"),
    @ApiResponse(responseCode = "400", description = "Invalid request body"),
    @ApiResponse(responseCode = "404", description = "Product not found")
  })
  @PutMapping(path = "/{productUuid}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  @PreAuthorize("hasRole('ADMIN')")
  @SecurityRequirement(name = "oauth2")
  public ProductDtoResponse updateProduct(
      @Parameter(description = "UUID of the product to be updated") @PathVariable UUID productUuid,
      @RequestBody @Valid ProductDtoRequest productDtoRequest) {
    return productService.updateProduct(productUuid, productDtoRequest);
  }

  /**
   * Delete product.
   *
   * @param productUuid the product uuid
   */
  @Operation(summary = "Delete a product", description = "Delete a product by its UUID")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Successfully deleted product"),
    @ApiResponse(responseCode = "404", description = "Product not found")
  })
  @DeleteMapping(path = "/{productUuid}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize("hasRole('ADMIN')")
  @SecurityRequirement(name = "oauth2")
  public void deleteProduct(
      @Parameter(description = "UUID of the product to be deleted") @PathVariable
          UUID productUuid) {
    productService.deleteProduct(productUuid);
  }
}
