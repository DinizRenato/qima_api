package com.qima.tech.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long productId) {
        super("Product ID " + productId + " not found.");
    }
}