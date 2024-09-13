package org.example.service.impl;

import lombok.AllArgsConstructor;
import org.example.entities.CategoryEntity;
import org.example.entities.ProductEntity;
import org.example.entities.ProductImageEntity;
import org.example.exception.ProductException;
import org.example.mapper.ProductMapper;
import org.example.models.product.*;
import org.example.repo.CategoryRepository;
import org.example.repo.ProductImageRepository;
import org.example.repo.ProductRepository;
import org.example.service.IProductService;
import org.example.storage.FileSaveFormat;
import org.example.storage.StorageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository repo;
    private final ProductImageRepository imageRepo;
    private final StorageService storageService;
    private final CategoryRepository categoryRepo;
    private final ProductMapper mapper;

    @Override
    public List<ProductItemDTO> get() {
        var list = new ArrayList<ProductItemDTO>();
        var data = repo.findAll();
        for (var product : data) {
            ProductItemDTO productItemDTO = mapper.ProductItemDTOByProduct(product);

//            productItemDTO.setCategory(product.getCategory().getName());
//            productItemDTO.setId(product.getId());
//            productItemDTO.setName(product.getName());
//            productItemDTO.setPrice(product.getPrice());
//            productItemDTO.setDescription(product.getDescription());

            var items = new ArrayList<String>();
            for (var img : product.getProductImages()) {
                items.add(img.getName());
            }
            productItemDTO.setFiles(items);
            list.add(productItemDTO);
        }
        return list;
    }

    @Override
    public Long saveProduct(ProductCreationModel productModel) {
        try{
            ProductEntity product = mapper.fromCreationModel(productModel);
            List<ProductImageEntity> images = new ArrayList<>();
            int index = 0;
            LocalDateTime date = LocalDateTime.now();
            for(var file:productModel.getFiles()){
                ProductImageEntity image = new ProductImageEntity(
                        0L,
                        storageService.saveImage(file, FileSaveFormat.WEBP),
                        index++,
                        new Date(),
                        false,
                        product);
                images.add(image);
            }
            product.setProductImages(images);
            product.setCreationTime(date);
            Optional<CategoryEntity> category = categoryRepo.findById(productModel.getCategoryId());
            if(category.isPresent()){
                product.setCategory(category.get());
            }
            else{
                throw new ProductException("Invalid category id");
            }

            ProductEntity savedProduct = repo.save(product);
            return savedProduct.getId();
        }
        catch (Exception e){
            throw new ProductException("Product save error"+"\n" + e.getMessage());
        }
    }

    @Override
    public PaginationResponse<ProductDto> getProducts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(
                page, size, Sort.by("id"));
        Page<ProductEntity> productsPage = repo.findAll(pageRequest);
        Iterable<ProductDto> categories = mapper.toDto(productsPage.getContent());
        return  new PaginationResponse<ProductDto>(categories,productsPage.getTotalElements());
    }

    @Override
    public ProductDto getProductById(Long id) {
        Optional<ProductEntity> product = repo.findById(id);
        if(product.isPresent()){
            return mapper.toDto(product.get());
        }
        else{
            throw new ProductException("Invalid Product id");
        }
    }

    @Override
    public boolean deleteProductById(Long id) throws IOException {
        Optional<ProductEntity> optCategory =  repo.findById(id);
        boolean isPresent = optCategory.isPresent();
        if(isPresent){
            ProductEntity product = optCategory.get();
            repo.delete(product);
            storageService.deleteImages(product.getProductImages().stream().map(ProductImageEntity::getName).toList());
        }
        return  isPresent;
    }

    @Override
    public boolean updateProduct(ProductUpdateModel productModel) throws IOException {
        Optional<ProductEntity> optProduct = repo.findById( productModel.getId());
        boolean isPresent = optProduct.isPresent();
        if(isPresent){
            ProductEntity product = mapper.fromUpdateModel(productModel);
            var oldImages = optProduct.get().getProductImages().toArray(ProductImageEntity[]::new);

            var existingImages = new ArrayList<>(Arrays.stream(oldImages)
                    .filter(x -> Arrays.stream(productModel.getImages())
                            .anyMatch(z -> Objects.equals(z.getId(), x.getId())))
                    .sorted(Comparator.comparing(ProductImageEntity::getId))
                    .toList());

            var modelImages = Arrays.stream(productModel.getImages())
                    .sorted(Comparator.comparing(ProductCreationImage::getId))
                    .toList();

            for (int i = 0; i < modelImages.size(); i++) {
                existingImages.get(i).setPriority( modelImages.get(i).getPriority());
            }

            product.setCreationTime(LocalDateTime.now());
            if(productModel.getFiles() != null) {
                for (var file : productModel.getFiles()) {
                    if (!file.getFile().isEmpty()) {
                        ProductImageEntity image = new ProductImageEntity(
                                0L,
                                storageService.saveImage(file.getFile(), FileSaveFormat.WEBP),
                                file.getPriority(),
                                new Date(),
                                false,
                                product
                        );
                        existingImages.add(image);
                    }
                }
            }

            product.setProductImages(existingImages);
            Optional<CategoryEntity> category = categoryRepo.findById(productModel.getCategoryId());
            if(category.isPresent()){
                product.setCategory(category.get());
            }
            else{
                throw new ProductException("Invalid category id");
            }
            repo.save(product);
            var imageToDelete =  Arrays.stream(oldImages)
                    .filter(x->Arrays.stream(existingImages.toArray(ProductImageEntity[]::new))
                            .anyMatch(z-> !Objects.equals(z.getId(), x.getId())))
                    .toArray(ProductImageEntity[]::new);
            imageRepo.deleteAll(List.of(imageToDelete));
            storageService.deleteImages(Arrays.stream(imageToDelete).map(ProductImageEntity::getName).toList());
        }
        return isPresent;
    }

}
