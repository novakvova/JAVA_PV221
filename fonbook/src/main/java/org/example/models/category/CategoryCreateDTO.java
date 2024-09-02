package org.example.models.category;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CategoryCreateDTO {
    private int id;
    private String name;
    private MultipartFile file;
    private String description;
}
