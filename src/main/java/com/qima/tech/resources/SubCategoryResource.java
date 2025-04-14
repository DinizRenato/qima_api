package com.qima.tech.resources;

import com.qima.tech.dtos.subcategory.CreateSubCategoryDTO;
import com.qima.tech.dtos.subcategory.SubCategoryDTO;
import com.qima.tech.dtos.subcategory.UpdateSubCategoryDTO;
import com.qima.tech.services.SubCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/subcategories")
public class SubCategoryResource {

    private final SubCategoryService subCategoryService;

    public SubCategoryResource(SubCategoryService subCategoryService){
        this.subCategoryService = subCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<SubCategoryDTO>> getAllSubCategories() {
        return ResponseEntity.ok(subCategoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubCategoryDTO> getSubCategoryById(@PathVariable Long id) {
        Optional<SubCategoryDTO> subCategory = subCategoryService.findById(id);
        return subCategory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/with-products")
    public ResponseEntity<SubCategoryDTO> getCategoryWithProducts(@PathVariable Long id) {
        Optional<SubCategoryDTO> subCategory = Optional.ofNullable(subCategoryService.getByIdWithProducts(id));
        return subCategory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SubCategoryDTO> createSubCategory(@RequestBody CreateSubCategoryDTO createSubCategory) {
        return ResponseEntity.ok(subCategoryService.create(createSubCategory));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SubCategoryDTO> updateSubCategory(@PathVariable Long id, @RequestBody UpdateSubCategoryDTO dto) {
        Optional<SubCategoryDTO> subCategory = Optional.ofNullable(subCategoryService.update(id, dto));
        return subCategory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        subCategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
