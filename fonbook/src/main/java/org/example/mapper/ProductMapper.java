package org.example.mapper;

import org.example.entities.ProductEntity;
import org.example.entities.ProductImageEntity;
import org.example.models.product.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.name", target = "category")
    @Mapping(source = "category.id", target = "category_id")
    ProductItemDTO ProductItemDTOByProduct(ProductEntity product);

    ProductEntity fromCreationModel(ProductCreationModel productModel);

    @Mapping(target = "productId", source = "product.id")
    ProductImageDto toDtoImageDto(ProductImageEntity image);

    @Mapping(target = "productImages", ignore = true)
    ProductEntity fromUpdateModel(ProductUpdateModel productModel);
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "images", source = "productImages")
    ProductDto toDto(ProductEntity product);
    List<ProductDto> toDto(Iterable<ProductEntity> product);
}
