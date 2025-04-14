package com.qima.tech.exceptions;

import com.qima.tech.entities.CategorySubCategory;

public class CategorySubCategoryAlreadyExists extends RuntimeException{
    public CategorySubCategoryAlreadyExists() {
        super("CategorySubCategoryAlreadyExists");
    }
}
