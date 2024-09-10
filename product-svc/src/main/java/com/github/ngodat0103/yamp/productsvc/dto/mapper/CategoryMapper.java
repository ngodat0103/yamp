package com.github.ngodat0103.yamp.productsvc.dto.mapper;


import com.github.ngodat0103.yamp.productsvc.dto.category.CategoryDtoRequest;
import com.github.ngodat0103.yamp.productsvc.dto.category.CategoryDtoResponse;
import com.github.ngodat0103.yamp.productsvc.persistence.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
   default Category mapToEntity(CategoryDtoRequest categoryDtoRequest, UUID createdBy){
       UUID parentCategoryUuid = null;
         if(categoryDtoRequest.getParentCategoryUuid()!=null){
              parentCategoryUuid = UUID.fromString(categoryDtoRequest.getParentCategoryUuid());
         }
       Category category = new Category(createdBy);
       category.setName(categoryDtoRequest.getName());
       category.setParentCategoryUuid(parentCategoryUuid);
       return category;
   }
    CategoryDtoResponse mapToDto(Category category);
}
