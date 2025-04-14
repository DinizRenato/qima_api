package com.qima.tech.dtos.subcategory;

import com.qima.tech.dtos.category.CategoryDTO;
import com.qima.tech.dtos.product.ProductDTO;
import com.qima.tech.entities.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class SubCategoryDTO {

    private Long id;
    private String name;
    private List<ProductDTO> products;

    public SubCategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static SubCategoryDTO fromEntity(SubCategory subCategory) {
        List<ProductDTO> productDTOs = subCategory.getProducts() != null
                ? subCategory.getProducts().stream().map(ProductDTO::fromEntity).toList()
                : null;

        return new SubCategoryDTO(
                subCategory.getId(),
                subCategory.getName(),
                productDTOs
        );
    }
}
