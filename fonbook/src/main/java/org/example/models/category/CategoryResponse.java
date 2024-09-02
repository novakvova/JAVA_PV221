package org.example.models.category;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryResponse {
    private Iterable<CategoryDto> categoryList;
    private long totalElements ;
}
