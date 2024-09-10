package com.github.ngodat0103.yamp.productsvc.controller;


import com.github.ngodat0103.yamp.productsvc.dto.PageDto;
import com.github.ngodat0103.yamp.productsvc.dto.product.ProductDtoRequest;
import com.github.ngodat0103.yamp.productsvc.dto.product.ProductDtoResponse;
import com.github.ngodat0103.yamp.productsvc.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/products")
public class ProductController {
    private final  ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping(path = "/all")
    public PageDto<ProductDtoResponse> getProducts(@RequestParam(required = false,defaultValue = "0") int page,
                                                   @RequestParam(required = false,defaultValue = "100") int size){
        return productService.getProducts(PageRequest.of(page,size));
    }

    @GetMapping(path = "/{slugName}")
    public ProductDtoResponse getProduct(@PathVariable String slugName){
        return productService.getProduct(slugName);
    }

    @GetMapping
    public ProductDtoResponse getProduct(@RequestParam UUID uuid){
        return productService.getProduct(uuid);
    }



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "oauth2")
    public ProductDtoResponse createProduct(@RequestBody @Valid ProductDtoRequest productDtoRequest){
        return productService.createProduct(productDtoRequest);
    }

    @PutMapping(path = "/{productUuid}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "oauth2")
    public ProductDtoResponse updateProduct(@PathVariable UUID productUuid, @RequestBody @Valid ProductDtoRequest productDtoRequest){
        return productService.updateProduct(productUuid, productDtoRequest);
    }

    @DeleteMapping(path = "/{productUuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "oauth2")
    public void deleteProduct(@PathVariable UUID productUuid){
        productService.deleteProduct(productUuid);
    }

}
