package org.example.service.impl;

import org.example.entities.CategoryEntity;
import org.example.mapper.CategoryMapper;
import org.example.models.category.CategoryCreateDTO;
import org.example.models.category.CategoryDto;
import org.example.models.category.CategoryResponse;
import org.example.repo.CategoryRepository;
import org.example.service.ICategoryService;
import org.example.storage.FileSaveFormat;
import org.example.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryRepository repo;
    @Autowired
    private StorageService storageService;
    @Autowired(required=true)
    private CategoryMapper mapper;

    @Override
    public Integer saveCategory(CategoryCreateDTO categoryModel) {
        try{
            CategoryEntity category = mapper.categoryEntityByCategoryCreateDTO(categoryModel);
            String imageName = storageService.saveImage(categoryModel.getFile(), FileSaveFormat.WEBP);
            category.setImage(imageName);
            category.setCreationTime(LocalDateTime.now());
            CategoryEntity savedCategory = repo.save(category);
            return savedCategory.getId();
        }
        catch (Exception e){
            return null;
        }
    }

    @Override
    public CategoryResponse getCategoryByName(int page, int size, String name) {
        Page<CategoryEntity> categoryPage = repo.findByNameContainingIgnoreCase(name, PageRequest.of(page, size));
        return new CategoryResponse(mapper.toDto(categoryPage.getContent()),categoryPage.getTotalElements());
    }

    @Override
    public CategoryDto getCategoryById(Integer id) {
        Optional<CategoryEntity> category = repo.findById(id);
        if(category.isPresent()){
            return mapper.toDto(category.get());
        }
        else{
            return null;
        }
    }

    @Override
    public boolean deleteCategoryById(Integer id) throws IOException {
        Optional<CategoryEntity> optCategory =  repo.findById(id);
        boolean isPresent = optCategory.isPresent();
        if(isPresent){
            CategoryEntity category = optCategory.get();
            repo.delete(category);
            storageService.deleteImage(category.getImage());
        }
        return  isPresent;
    }

    @Override
    public boolean updateCategory(CategoryCreateDTO categoryModel) throws IOException {
        Optional<CategoryEntity> optCategory = repo.findById(categoryModel.getId());
        boolean isPresent = optCategory.isPresent();
        if(isPresent){
            CategoryEntity category = mapper.categoryEntityByCategoryCreateDTO(categoryModel);
            category.setCreationTime(LocalDateTime.now());
            if(!categoryModel.getFile().isEmpty() ){
                storageService.deleteImage(optCategory.get().getImage());
                String imageName = storageService.saveImage(categoryModel.getFile(),FileSaveFormat.WEBP);
                category.setImage(imageName);
            }
            repo.save(category);
        }
        return isPresent;
    }
}
