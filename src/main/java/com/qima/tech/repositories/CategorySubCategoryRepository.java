package com.qima.tech.repositories;

import com.qima.tech.entities.CategorySubCategory;
import com.qima.tech.keys.CategorySubCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CategorySubCategoryRepository extends JpaRepository<CategorySubCategory, CategorySubCategoryId> {

    List<CategorySubCategory> findByCategoryId(Long categoryId);

    List<CategorySubCategory> findBySubCategoryId(Long subCategoryId);

    @Modifying
    @Transactional
    @Query("DELETE FROM CategorySubCategory csc WHERE csc.id.categoryId = :categoryId")
    void deleteByCategoryId(Long categoryId);

    @Modifying
    @Transactional
    @Query("DELETE FROM CategorySubCategory csc WHERE csc.id.subCategoryId = :subCategoryId")
    void deleteBySubCategoryId(Long subCategoryId);

}
