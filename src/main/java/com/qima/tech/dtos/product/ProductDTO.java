package com.qima.tech.dtos.product;

import com.qima.tech.dtos.category.CategoryDTO;
import com.qima.tech.dtos.subcategory.SubCategoryDTO;
import com.qima.tech.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean available;
    private CategoryDTO category;
    private SubCategoryDTO subCategory;

    public static ProductDTO fromEntity(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getAvailable(),
                new CategoryDTO(product.getCategory().getId(), product.getCategory().getName()),
                product.getSubCategory() != null ?
                new SubCategoryDTO(product.getSubCategory().getId(), product.getSubCategory().getName()) : null
        );
    }
}
