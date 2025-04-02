package com.qima.tech.services;

import com.qima.tech.dtos.category.CategoryDTO;
import com.qima.tech.dtos.category.CreateCategoryDTO;
import com.qima.tech.dtos.category.UpdateCategoryDTO;
import com.qima.tech.entities.Category;
import com.qima.tech.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll().stream().map(this::toDto).toList();
    }

    public Optional<CategoryDTO> getCategoryWithProducts(Long id) {
        return categoryRepository.findByIdWithProducts(id)
                .map(CategoryDTO::fromEntity);
    }

    public Optional<CategoryDTO> findById(Long id) {
        return categoryRepository.findById(id).map(this::toDto);
    }

    public CategoryDTO save(CreateCategoryDTO createCategory) {
        Category category = new Category();
        category.setName(createCategory.name());
        return toDto(categoryRepository.save(category));
    }

    public CategoryDTO update(Long id, UpdateCategoryDTO updateCategory) {
        Category category = categoryRepository.findById(id).orElseThrow();
        category.setName(updateCategory.name());
        return toDto(categoryRepository.save(category));
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    private CategoryDTO toDto(Category category) {
        return new CategoryDTO(category.getId(), category.getName());
    }
}
