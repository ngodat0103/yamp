package com.github.ngodat0103.yamp.productsvc.controller;


import com.github.ngodat0103.yamp.productsvc.dto.ProductDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ProductController {
    @GetMapping
    public ProductDto getProducts(){
        return new ProductDto();
    }

}
