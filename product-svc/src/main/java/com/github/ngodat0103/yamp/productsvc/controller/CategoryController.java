package com.github.ngodat0103.yamp.productsvc.controller;

import com.github.ngodat0103.yamp.productsvc.dto.category.CategoryDtoRequest;
import com.github.ngodat0103.yamp.productsvc.dto.category.CategoryDtoResponse;
import com.github.ngodat0103.yamp.productsvc.dto.PageDto;
import com.github.ngodat0103.yamp.productsvc.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Slf4j
@RequestMapping(value = "/categories")
public class CategoryController {


   private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(path = "/all")
    public PageDto<CategoryDtoResponse> getCategory(@RequestParam(required = false,defaultValue = "0") int page,
                                                    @RequestParam(required = false,defaultValue = "100")  int size){
         return categoryService.getAllCategories(PageRequest.of(page,size));
    }

    @GetMapping(path = "/{slugName}")
    public CategoryDtoResponse getCategory(@PathVariable String slugName){
        return  categoryService.getCategory(slugName);
    }

    @GetMapping
    public CategoryDtoResponse getCategoryByUuid(@RequestParam UUID categoryUuid){
        return categoryService.getCategory(categoryUuid);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = "application/json",produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "oauth2")
    public CategoryDtoResponse createCategory(@RequestBody @Valid CategoryDtoRequest categoryDtoRequest){
        return categoryService.createCategory(categoryDtoRequest);
    }



    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(consumes = "application/json",produces = "application/json",path = "/{categoryUuid}")
    @SecurityRequirement(name = "oauth2")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CategoryDtoResponse updateCategory(@RequestBody @Valid CategoryDtoRequest categoryDtoRequest, @PathVariable UUID categoryUuid){
        return categoryService.updateCategory(categoryUuid, categoryDtoRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{categoryUuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = "oauth2")

    public void deleteCategory(@PathVariable UUID categoryUuid){
        categoryService.deleteCategory(categoryUuid);
    }
}
