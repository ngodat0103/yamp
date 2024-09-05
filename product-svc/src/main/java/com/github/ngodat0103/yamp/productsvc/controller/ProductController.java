package com.github.ngodat0103.yamp.productsvc.controller;


import com.github.ngodat0103.yamp.productsvc.dto.product.ProductDto;
import com.github.ngodat0103.yamp.productsvc.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping(path = "/products")
public class ProductController {
    private final  ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping(path = "/all")
    public Set<ProductDto> getProducts(){
        return productService.getProducts();
    }

    @GetMapping(path = "/{slugName}")
    public ProductDto getProduct(@PathVariable String slugName){
        return productService.getProduct(slugName);
    }

    @GetMapping
    public ProductDto getProduct(@RequestParam UUID uuid){
        return productService.getProduct(uuid);
    }



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ProductDto createProduct(@RequestBody @Valid ProductDto productDto){
        return productService.createProduct(productDto);
    }

    @PutMapping(path = "/{productUuid}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasRole('ADMIN')")
    public ProductDto updateProduct(@PathVariable UUID productUuid, @RequestBody @Valid ProductDto productDto){
        return productService.updateProduct(productUuid,productDto);
    }

    @DeleteMapping(path = "/{productUuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteProduct(@PathVariable UUID productUuid){
        productService.deleteProduct(productUuid);
    }

}
