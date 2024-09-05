package com.github.ngodat0103.yamp.productsvc.controller;

import com.github.ngodat0103.yamp.productsvc.dto.CategoryDto;
import com.github.ngodat0103.yamp.productsvc.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping(value = "/categories")
public class CategoryController {


   private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public Set<CategoryDto> getCategory(){
        return categoryService.getAllCategories();
    }

    @GetMapping(path = "/filter")
    public CategoryDto getCategoryBySlugName(@RequestParam(name = "slugName") String slugName){
        return categoryService.getCategory(slugName);
    }

    @GetMapping("/{categoryUuid}")
    public CategoryDto getCategoryByUuid(@PathVariable UUID categoryUuid){
        return categoryService.getCategory(categoryUuid);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = "application/json",produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "oauth2")
    public CategoryDto createCategory(@RequestBody @Valid CategoryDto categoryDto){
        return categoryService.createCategory(categoryDto);
    }



    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(consumes = "application/json",produces = "application/json",path = "/{categoryUuid}")
    @SecurityRequirement(name = "oauth2")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CategoryDto updateCategory(@RequestBody @Valid CategoryDto categoryDto,@PathVariable UUID categoryUuid){
        return categoryService.updateCategory(categoryUuid,categoryDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{categoryUuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = "oauth2")

    public void deleteCategory(@PathVariable UUID categoryUuid){
        categoryService.deleteCategory(categoryUuid);
    }
}
