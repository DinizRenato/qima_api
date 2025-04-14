package com.qima.tech.keys;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategorySubCategoryId {
    private Long categoryId;
    private Long subCategoryId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategorySubCategoryId)) return false;
        CategorySubCategoryId that = (CategorySubCategoryId) o;
        return Objects.equals(categoryId, that.categoryId) &&
                Objects.equals(subCategoryId, that.subCategoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, subCategoryId);
    }
}
