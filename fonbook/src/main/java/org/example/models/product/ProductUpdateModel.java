package org.example.models.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateModel extends ProductModel{
    private ProductCreationFile[] files;
    private ProductCreationImage[] images;
}
