package org.example.productsvc.controller;

import org.example.productsvc.dto.ProductCategoryDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @GetMapping(produces = "application/json")
    public Set<ProductCategoryDto> getProductsByCategory(){
        return Set.of(new ProductCategoryDto());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCategory(@RequestBody ProductCategoryDto productCategoryDto){
        return;
    }
    @PutMapping("/{categoryUuid}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateCategory(){
    }

    @DeleteMapping("/{categoryUuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(){
    }
}
