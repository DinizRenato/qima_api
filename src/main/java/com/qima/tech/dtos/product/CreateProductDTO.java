package com.qima.tech.dtos.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateProductDTO {

    private String name;
    private String description;
    private BigDecimal price;
    private Boolean available;
    private Long categoryId;

}
