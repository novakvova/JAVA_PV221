package org.example.mapper;

import org.example.entities.CategoryEntity;
import org.example.models.category.CategoryCreateDTO;
import org.example.models.category.CategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "image", ignore = true)
    CategoryEntity categoryEntityByCategoryCreateDTO(CategoryCreateDTO category);
    CategoryDto toDto(CategoryEntity category);
    Iterable<CategoryDto> toDto(Iterable<CategoryEntity> category);
}
