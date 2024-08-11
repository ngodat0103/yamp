package org.example.productsvc.controller;

import jakarta.validation.Valid;
import org.example.productsvc.dto.CategoryDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @GetMapping(produces = "application/json")
    public Set<CategoryDto> getProductsByCategory(){
        return Set.of(new CategoryDto());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@RequestBody @Valid CategoryDto categoryDto){
        return categoryDto;

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
