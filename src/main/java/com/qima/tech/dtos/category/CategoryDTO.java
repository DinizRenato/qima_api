package com.qima.tech.dtos.category;

import com.qima.tech.dtos.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    private Long id;
    private String name;
    private List<ProductDTO> products;

    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static CategoryDTO fromEntity(com.qima.tech.entities.Category category) {
        List<ProductDTO> productDTOs = category.getProducts() != null
                ? category.getProducts().stream().map(ProductDTO::fromEntity).collect(Collectors.toList())
                : null;
        return new CategoryDTO(category.getId(), category.getName(), productDTOs);
    }
}
