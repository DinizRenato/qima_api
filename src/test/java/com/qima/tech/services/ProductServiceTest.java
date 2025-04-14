package com.qima.tech.services;

import com.qima.tech.dtos.product.CreateProductDTO;
import com.qima.tech.dtos.product.UpdateProductDTO;
import com.qima.tech.entities.Category;
import com.qima.tech.entities.Product;
import com.qima.tech.exceptions.CategoryNotFoundException;
import com.qima.tech.exceptions.ProductNotFoundException;
import com.qima.tech.repositories.CategoryRepository;
import com.qima.tech.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    private Category category;
    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        category = new Category(1L, "Electronics");
        product = new Product(1L, "Laptop", "A powerful laptop", new BigDecimal("1500.00"), category, null, true);
    }

    @Test
    void createProduct_ShouldReturnProductDTO_WhenValidDataIsProvided() {
        CreateProductDTO dto = new CreateProductDTO("Laptop", "A powerful laptop", new BigDecimal("1500.00"), true, 1L, null);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        var result = productService.createProduct(dto);

        assertNotNull(result);
        assertEquals(dto.getName(), result.getName());
        assertEquals(dto.getDescription(), result.getDescription());
        assertEquals(dto.getPrice(), result.getPrice());
        assertEquals(dto.getAvailable(), result.getAvailable());

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void createProduct_ShouldThrowException_WhenCategoryNotFound() {
        CreateProductDTO dto = new CreateProductDTO("Laptop", "A powerful laptop", new BigDecimal("1500.00"), true, 2L, null);

        when(categoryRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> productService.createProduct(dto));
    }

    @Test
    void getProductById_ShouldReturnProductDTO_WhenProductExists() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        var result = productService.getProductById(1L);

        assertTrue(result.isPresent());
        assertEquals(product.getName(), result.get().getName());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void getProductById_ShouldReturnEmpty_WhenProductDoesNotExist() {
        when(productRepository.findById(2L)).thenReturn(Optional.empty());

        var result = productService.getProductById(2L);

        assertTrue(result.isEmpty());
    }

    @Test
    void getAllProducts_ShouldReturnListOfProductDTOs() {
        when(productRepository.findAll()).thenReturn(List.of(product));

        var result = productService.getAllProducts();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void updateProduct_ShouldReturnUpdatedProductDTO_WhenProductExists() {
        UpdateProductDTO dto = new UpdateProductDTO("Updated Laptop", "Updated description", new BigDecimal("1600.00"), true, 1L, null);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        var result = productService.updateProduct(1L, dto);

        assertEquals(dto.getName(), result.getName());
        assertEquals(dto.getDescription(), result.getDescription());
        assertEquals(dto.getPrice(), result.getPrice());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void updateProduct_ShouldReturnEmpty_WhenProductDoesNotExist() {
        UpdateProductDTO dto = new UpdateProductDTO("Updated Laptop", "Updated description", new BigDecimal("1600.00"), true, 1L, null);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ProductNotFoundException.class, () -> {
            productService.updateProduct(1L, dto);
        });

        assertEquals("Product ID 1 not found.", exception.getMessage());

    }

    @Test
    void deleteProduct_ShouldReturnTrue_WhenProductExists() {
        when(productRepository.existsById(1L)).thenReturn(true);

        var result = productService.deleteProduct(1L);

        assertTrue(result);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteProduct_ShouldReturnFalse_WhenProductDoesNotExist() {
        when(productRepository.existsById(2L)).thenReturn(false);

        var result = productService.deleteProduct(2L);

        assertFalse(result);
    }
}

