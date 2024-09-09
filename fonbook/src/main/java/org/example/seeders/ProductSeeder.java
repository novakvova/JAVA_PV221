package org.example.seeders;

import com.github.javafaker.Faker;
import org.example.entities.CategoryEntity;
import org.example.entities.ProductEntity;
import org.example.entities.ProductImageEntity;
import org.example.repo.CategoryRepository;
import org.example.storage.FileSaveFormat;
import org.example.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
public class ProductSeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final StorageService storageService;
    private final Faker faker = new Faker();
    public ProductSeeder(StorageService storageService,CategoryRepository categoryRepository) {
        this.storageService = storageService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws IOException {
        if (categoryRepository.count() == 0) {
            List<CategoryEntity> categories = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                CategoryEntity category = new CategoryEntity(
                        0,
                        faker.commerce().productName(),
                        storageService.saveImage("https://picsum.photos/300/300", FileSaveFormat.WEBP),
                        faker.lorem().sentence(10),
                        LocalDateTime.now(),
                        new ArrayList<ProductEntity>()
                );
                List<ProductEntity> products = new ArrayList<>();
                for (int s = 0; s < 5; s++) {
                    ProductEntity product = new ProductEntity(
                            null,
                            faker.commerce().productName(),
                            faker.lorem().sentence(10),
                            LocalDateTime.now(),
                            faker.number().randomDouble(2, 10, 100),
                            faker.number().randomDouble(2, 0, 20),
                            category,
                            new ArrayList<ProductImageEntity>()
                    );
                    List<ProductImageEntity> images = new ArrayList<ProductImageEntity>();
                    for(int k = 0; k < 3; k++){
                        images.add(new ProductImageEntity(
                                null,
                                storageService.saveImage("https://picsum.photos/300/300", FileSaveFormat.WEBP),
                                k,
                                new Date(),
                                false,
                                product)
                        );
                    }
                    product.setProductImages(images);
                    products.add(product);
                }
                category.setProducts(products);
                categories.add(category);
            }
            categoryRepository.saveAll(categories);
        }
    }
}