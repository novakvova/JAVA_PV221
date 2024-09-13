package org.example.service;

import org.example.models.product.*;

import java.io.IOException;
import java.util.List;

public interface IProductService {
    List<ProductItemDTO> get();

    Long saveProduct(ProductCreationModel productModel);
    PaginationResponse<ProductDto> getProducts(int page, int size);
    ProductDto getProductById(Long id);
    boolean deleteProductById(Long id) throws IOException;
    boolean updateProduct(ProductUpdateModel productModel) throws IOException;
}
