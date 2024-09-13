package org.example.models.product;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductItemDTO {
    private Long id;
    private String name;
    private double price;
    private String description;
    private String category;
    private int category_id;
    private List<String> files = new ArrayList<>();
}