package com.qima.tech.services;

import com.qima.tech.dtos.product.CreateProductDTO;
import com.qima.tech.dtos.product.ProductDTO;
import com.qima.tech.dtos.product.UpdateProductDTO;
import com.qima.tech.entities.Category;
import com.qima.tech.entities.Product;
import com.qima.tech.entities.SubCategory;
import com.qima.tech.exceptions.CategoryNotFoundException;
import com.qima.tech.exceptions.ProductNotFoundException;
import com.qima.tech.exceptions.SubCategoryNotFoundException;
import com.qima.tech.repositories.CategoryRepository;
import com.qima.tech.repositories.ProductRepository;
import com.qima.tech.repositories.SubCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, SubCategoryRepository subCategoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.subCategoryRepository = subCategoryRepository;
    }

    public ProductDTO createProduct(CreateProductDTO dto) {

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(dto.getCategoryId()));

        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setAvailable(dto.getAvailable());
        product.setCategory(category);

        if (dto.getSubCategoryId() != null) {
            SubCategory subCategory = subCategoryRepository.findById(dto.getSubCategoryId())
                    .orElseThrow(() -> new SubCategoryNotFoundException(dto.getSubCategoryId()));
            product.setSubCategory(subCategory);
        }

        product = productRepository.save(product);
        return ProductDTO.fromEntity(product);
    }

    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id).map(ProductDTO::fromEntity);
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public ProductDTO updateProduct(Long id, UpdateProductDTO dto) {

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(dto.getCategoryId()));

        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setCategory(category);
        product.setAvailable(dto.getAvailable());

        if (dto.getSubCategoryId() != null) {
            SubCategory subCategory = subCategoryRepository.findById(dto.getSubCategoryId())
                    .orElseThrow(() -> new SubCategoryNotFoundException(dto.getSubCategoryId()));
            product.setSubCategory(subCategory);
        } else {
            product.setSubCategory(null);
        }

        productRepository.save(product);
        return ProductDTO.fromEntity(product);

    }

    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

