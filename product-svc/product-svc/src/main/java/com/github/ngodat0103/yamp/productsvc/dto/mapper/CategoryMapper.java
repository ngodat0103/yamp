package com.github.ngodat0103.yamp.productsvc.dto.mapper;


import com.github.ngodat0103.yamp.productsvc.dto.CategoryDto;
import com.github.ngodat0103.yamp.productsvc.persistence.entity.Category;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category mapToEntity(CategoryDto categoryDto);
    default  Category mapToEntity(CategoryDto categoryDto, UUID createdBy){
        return new Category(categoryDto,createdBy);
    }
    CategoryDto mapToDto(Category category);
}
