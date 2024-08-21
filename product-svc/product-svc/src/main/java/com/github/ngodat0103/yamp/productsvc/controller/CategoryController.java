package com.github.ngodat0103.yamp.productsvc.controller;
import com.github.ngodat0103.yamp.productsvc.dto.CategoryDto;
import com.github.ngodat0103.yamp.productsvc.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import java.util.Set;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping(value = "/category")
public class CategoryController {


   private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    public Set<CategoryDto> getCategory(){
        return categoryService.getAllCategories();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = "application/json",produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@RequestBody @Valid CategoryDto categoryDto, Authentication authentication){
     Assert.notNull(authentication,"Authentication object is required");
     UUID createBy = UUID.fromString(authentication.getName());
        return categoryService.createCategory(categoryDto,createBy);
    }



    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(consumes = "application/json",produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CategoryDto updateCategory(@RequestBody @Valid CategoryDto categoryDto,  Authentication authentication){
        Assert.notNull(authentication,"Authentication object is required");
        return categoryService.updateCategory(categoryDto,UUID.fromString(authentication.getName()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{categoryUuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable UUID categoryUuid){
        categoryService.deleteCategory(categoryUuid);
    }
}
