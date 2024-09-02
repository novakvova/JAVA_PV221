package org.example.service;

import org.example.models.category.CategoryCreateDTO;
import org.example.models.category.CategoryDto;
import org.example.models.category.CategoryResponse;

import java.io.IOException;

public interface ICategoryService {
    public Integer saveCategory(CategoryCreateDTO categoryModel);
    public CategoryResponse getCategoryByName(int page, int size, String name) ;
    public CategoryDto getCategoryById(Integer id);
    public boolean deleteCategoryById(Integer id) throws IOException;
    public boolean updateCategory(CategoryCreateDTO categoryModel) throws IOException;
}
