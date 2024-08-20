package com.github.ngodat0103.yamp.productsvc.dto.mapper;


import com.github.ngodat0103.yamp.productsvc.dto.CategoryDto;
import com.github.ngodat0103.yamp.productsvc.persistence.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "subCategories", ignore = true)
    @Mapping(target = "productSet", ignore = true)
    @Mapping(target = "parentCategory", ignore = true)
    Category mapToEntity(CategoryDto productDto);
    CategoryDto mapToDto(Category category);
}
