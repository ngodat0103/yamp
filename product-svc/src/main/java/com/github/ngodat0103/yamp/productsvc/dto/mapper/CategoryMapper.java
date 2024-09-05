package com.github.ngodat0103.yamp.productsvc.dto.mapper;


import com.github.ngodat0103.yamp.productsvc.dto.CategoryDto;
import com.github.ngodat0103.yamp.productsvc.persistence.entity.Category;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring",unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface CategoryMapper {
    Category mapToEntity(CategoryDto categoryDto);
   default Category mapToEntity(CategoryDto categoryDto, UUID createdBy){
       Category category = new Category(createdBy);
       category.setName(categoryDto.getName());
       category.setParentCategoryUuid(categoryDto.getParentCategoryUuid());
       return category;
   }
    CategoryDto mapToDto(Category category);
}
