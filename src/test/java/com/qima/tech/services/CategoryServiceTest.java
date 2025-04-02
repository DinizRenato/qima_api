package com.qima.tech.services;

import com.qima.tech.dtos.category.CategoryDTO;
import com.qima.tech.dtos.category.CreateCategoryDTO;
import com.qima.tech.dtos.category.UpdateCategoryDTO;
import com.qima.tech.entities.Category;
import com.qima.tech.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;
    private CategoryDTO categoryDto;
    private CreateCategoryDTO createCategory;
    private UpdateCategoryDTO updateCategory;

    @BeforeEach
    void setUp() {
        category = new Category(1L, "Electronics");
        categoryDto = new CategoryDTO(1L, "Electronics");
        createCategory = new CreateCategoryDTO("Electronics");
        updateCategory = new UpdateCategoryDTO("Updated Electronics");
    }

    @Test
    void testFindAll() {
        when(categoryRepository.findAll()).thenReturn(List.of(category));

        List<CategoryDTO> result = categoryService.findAll();

        assertEquals(1, result.size());
        assertEquals(categoryDto.getId(), result.get(0).getId());
        assertEquals(categoryDto.getName(), result.get(0).getName());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Optional<CategoryDTO> result = categoryService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(categoryDto.getId(), result.get().getId());
        assertEquals(categoryDto.getName(), result.get().getName());
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void testSave() {
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        CategoryDTO result = categoryService.save(createCategory);

        assertNotNull(result);
        assertEquals(categoryDto.getId(), result.getId());
        assertEquals(categoryDto.getName(), result.getName());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void testUpdate() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(new Category(1L, updateCategory.name()));

        CategoryDTO result = categoryService.update(1L, updateCategory);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(updateCategory.name(), result.getName());
        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void testDeleteById() {
        doNothing().when(categoryRepository).deleteById(1L);

        assertDoesNotThrow(() -> categoryService.deleteById(1L));
        verify(categoryRepository, times(1)).deleteById(1L);
    }

    @Test
    void testSaveDuplicateName() {
        when(categoryRepository.save(any(Category.class)))
                .thenThrow(new DataIntegrityViolationException("Duplicate entry"));

        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
            categoryService.save(createCategory);
        });

        assertEquals("Duplicate entry", exception.getMessage());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }
}

