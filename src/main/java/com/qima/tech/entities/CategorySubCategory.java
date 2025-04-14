package com.qima.tech.entities;

import com.qima.tech.keys.CategorySubCategoryId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "category_sub_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategorySubCategory {

    @EmbeddedId
    private CategorySubCategoryId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("categoryId")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("subCategoryId")
    private SubCategory subCategory;
}
