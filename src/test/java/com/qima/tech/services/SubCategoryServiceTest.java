package com.qima.tech.services;

import com.qima.tech.dtos.subcategory.CreateSubCategoryDTO;
import com.qima.tech.dtos.subcategory.SubCategoryDTO;
import com.qima.tech.entities.Category;
import com.qima.tech.entities.Product;
import com.qima.tech.entities.SubCategory;
import com.qima.tech.repositories.SubCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SubCategoryServiceTest {

    @Mock
    private SubCategoryRepository subCategoryRepository;

    @InjectMocks
    private SubCategoryService subCategoryService;

    private SubCategory subCategory;

    @BeforeEach
    void setUp() {
        subCategory = new SubCategory(1L, "Male", null, null);
    }

    @Test
    void create_ShouldReturnSubCategoryDTO_WhenValidDataIsProvided() {

        when(subCategoryRepository.save(any(SubCategory.class))).thenReturn(subCategory);

        CreateSubCategoryDTO dto = new CreateSubCategoryDTO("Male");

        var result = subCategoryService.create(dto);

        assertNotNull(result);
        assertEquals(dto.name(), result.getName());

    }

}
