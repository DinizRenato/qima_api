package com.qima.tech.services;

import com.qima.tech.dtos.category.CategoryDTO;
import com.qima.tech.dtos.subcategory.SubCategoryDTO;
import com.qima.tech.entities.Category;
import com.qima.tech.entities.CategorySubCategory;
import com.qima.tech.entities.SubCategory;
import com.qima.tech.exceptions.CategoryNotFoundException;
import com.qima.tech.exceptions.CategorySubCategoryAlreadyExists;
import com.qima.tech.exceptions.SubCategoryNotFoundException;
import com.qima.tech.keys.CategorySubCategoryId;
import com.qima.tech.repositories.CategoryRepository;
import com.qima.tech.repositories.CategorySubCategoryRepository;
import com.qima.tech.repositories.SubCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategorySubCategoryService {

    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final CategorySubCategoryRepository categorySubCategoryRepository;

    public CategorySubCategoryService(
            CategoryRepository categoryRepository,
            SubCategoryRepository subCategoryRepository,
            CategorySubCategoryRepository categorySubCategoryRepository
    ) {
        this.categoryRepository = categoryRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.categorySubCategoryRepository = categorySubCategoryRepository;
    }

    public void create(Long categoryId, Long subCategoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        SubCategory subCategory = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new SubCategoryNotFoundException(subCategoryId));

        CategorySubCategoryId id = new CategorySubCategoryId(categoryId, subCategoryId);

        if (!categorySubCategoryRepository.existsById(id)) {
            CategorySubCategory link = new CategorySubCategory(id, category, subCategory);
            categorySubCategoryRepository.save(link);
        } else {
            throw new CategorySubCategoryAlreadyExists();
        }
    }

    public void delete(Long categoryId, Long subCategoryId) {
        CategorySubCategoryId id = new CategorySubCategoryId(categoryId, subCategoryId);
        categorySubCategoryRepository.deleteById(id);
    }

    public List<SubCategoryDTO> getSubCategoriesByCategory(Long categoryId) {

        return categorySubCategoryRepository.findByCategoryId(categoryId)
                .stream()
                .map(CategorySubCategory::getSubCategory)
                .map(SubCategoryDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<CategoryDTO> getCategoriesBySubCategory(Long subCategoryId) {
        return categorySubCategoryRepository.findBySubCategoryId(subCategoryId)
                .stream()
                .map(CategorySubCategory::getCategory)
                .map(CategoryDTO::fromEntity)
                .collect(Collectors.toList());
    }


}
