package com.qima.tech.services;

import com.qima.tech.dtos.subcategory.CreateSubCategoryDTO;
import com.qima.tech.dtos.subcategory.SubCategoryDTO;
import com.qima.tech.dtos.subcategory.UpdateSubCategoryDTO;
import com.qima.tech.entities.Product;
import com.qima.tech.entities.SubCategory;
import com.qima.tech.exceptions.SubCategoryNotFoundException;
import com.qima.tech.repositories.CategorySubCategoryRepository;
import com.qima.tech.repositories.ProductRepository;
import com.qima.tech.repositories.SubCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;
    private final ProductRepository productRepository;
    private final CategorySubCategoryRepository categorySubCategoryRepository;

    public SubCategoryService(SubCategoryRepository subCategoryRepository, ProductRepository productRepository, CategorySubCategoryRepository categorySubCategoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
        this.productRepository = productRepository;
        this.categorySubCategoryRepository = categorySubCategoryRepository;
    }

    public SubCategoryDTO create(CreateSubCategoryDTO dto) {
        SubCategory entity = new SubCategory();
        entity.setName(dto.name());
        return toDto(subCategoryRepository.save(entity));
    }

    public List<SubCategoryDTO> findAll() {
        return subCategoryRepository.findAll().stream()
                .map(SubCategoryDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public Optional<SubCategoryDTO> findById(Long id) {
        return subCategoryRepository.findById(id).map(SubCategoryDTO::fromEntity);
    }

    public SubCategoryDTO getByIdWithProducts(Long id) {
        SubCategory subCategory = subCategoryRepository.findByIdWithProducts(id)
                .orElseThrow(() -> new SubCategoryNotFoundException(id));

        return SubCategoryDTO.fromEntity(subCategory);
    }

    public SubCategoryDTO update(Long id, UpdateSubCategoryDTO dto) {
        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new SubCategoryNotFoundException(id));

        subCategory.setName(dto.getName());
        return SubCategoryDTO.fromEntity(subCategoryRepository.save(subCategory));
    }

    @Transactional
    public void delete(Long id) {
        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new SubCategoryNotFoundException(id));

        List<Product> products = productRepository.findBySubCategory(subCategory);
        for (Product product : products) {
            product.setSubCategory(null);
        }

        productRepository.saveAll(products);

        categorySubCategoryRepository.deleteBySubCategoryId(subCategory.getId());
        subCategoryRepository.delete(subCategory);
    }

    private SubCategoryDTO toDto(SubCategory subCategory) {
        return new SubCategoryDTO(subCategory.getId(), subCategory.getName());
    }

}

