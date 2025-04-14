package com.qima.tech.resources;

import com.qima.tech.dtos.category.CategoryDTO;
import com.qima.tech.dtos.subcategory.SubCategoryDTO;
import com.qima.tech.keys.CategorySubCategoryId;
import com.qima.tech.services.CategorySubCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category-subcategories")
public class CategorySubCategoryResource {

    private final CategorySubCategoryService service;

    public CategorySubCategoryResource(CategorySubCategoryService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CategorySubCategoryId id) {
        service.create(id.getCategoryId(), id.getSubCategoryId());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{categoryId}/{subCategoryId}")
    public ResponseEntity<Void> deleteRelation(@PathVariable Long categoryId, @PathVariable Long subCategoryId) {
        service.delete(categoryId, subCategoryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<List<SubCategoryDTO>> getByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(service.getSubCategoriesByCategory(categoryId));
    }

    @GetMapping("/by-subcategory/{subCategoryId}")
    public ResponseEntity<List<SubCategoryDTO>> getBySubCategory(@PathVariable Long subCategoryId) {
        return ResponseEntity.ok(service.getSubCategoriesByCategory(subCategoryId));
    }

    @GetMapping("/categories-by-subcategory/{subCategoryId}")
    public ResponseEntity<List<CategoryDTO>> getCategoriesBySubCategory(@PathVariable Long subCategoryId) {
        return ResponseEntity.ok(service.getCategoriesBySubCategory(subCategoryId));
    }


}
