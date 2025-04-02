package com.qima.tech.exceptions;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(Long categoryId) {
        super("Category ID " + categoryId + " not found.");
    }
}
