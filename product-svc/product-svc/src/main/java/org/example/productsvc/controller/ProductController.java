package org.example.productsvc.controller;


import org.example.productsvc.dto.ProductDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
    @GetMapping
    public ProductDto getProducts(){
        return new ProductDto();
    }

}
