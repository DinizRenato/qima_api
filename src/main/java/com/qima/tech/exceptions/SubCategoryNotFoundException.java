package com.qima.tech.exceptions;

public class SubCategoryNotFoundException extends RuntimeException {
    public SubCategoryNotFoundException(Long subCategoryId) {
        super("Sub Category ID " + subCategoryId + " not found.");
    }
}