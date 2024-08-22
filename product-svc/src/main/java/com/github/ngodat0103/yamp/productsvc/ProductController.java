package com.github.ngodat0103.yamp.productsvc;


import com.github.ngodat0103.yamp.productsvc.dto.ProductDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    @GetMapping
    public ProductDto getProducts(){
        return new ProductDto();
    }

}
