package com.genpact.onlineShoppingApp.service;

import com.genpact.onlineShoppingApp.entity.Product;
import com.genpact.onlineShoppingApp.repository.ProductRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product testProduct;
    private ObjectId testId;

    @BeforeEach
    void setUp() {
        testId = new ObjectId();
        testProduct = new Product();
        testProduct.setId(testId);
        testProduct.setName("Test Product");
        testProduct.setDescription("Test Description");
        testProduct.setPrice(99.99);
        testProduct.setQuantity(10);
    }

    @Test
    void getAllProducts_ShouldReturnListOfProducts() {
        // Arrange
        when(productRepository.findAll()).thenReturn(Arrays.asList(testProduct));

        // Act
        List<Product> result = productService.getAllProducts();

        // Assert
        assertEquals(1, result.size());
        assertEquals(testProduct, result.get(0));
    }

    @Test
    void getProductByName_ShouldReturnProduct() {
        // Arrange
        when(productRepository.findByName("Test Product")).thenReturn(Optional.of(testProduct));

        // Act
        Product result = productService.getProductByName("Test Product");

        // Assert
        assertNotNull(result);
        assertEquals("Test Product", result.getName());
    }

    @Test
    void addProduct_ShouldReturnSavedProduct() {
        // Arrange
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        // Act
        Product result = productService.addProduct(testProduct);

        // Assert
        assertNotNull(result);
        assertEquals(testProduct.getName(), result.getName());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void deleteProduct_ShouldReturnTrue_WhenProductExists() {
        // Arrange
        when(productRepository.findById(testId)).thenReturn(Optional.of(testProduct));

        // Act
        boolean result = productService.deleteProduct(testId);

        // Assert
        assertTrue(result);
        verify(productRepository).deleteById(testId);
    }

    @Test
    void reduceQuantity_ShouldReduceProductQuantity() {
        // Arrange
        when(productRepository.findById(testId)).thenReturn(Optional.of(testProduct));
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        // Act
        Product result = productService.reduceQuantity(testId, 5);

        // Assert
        assertNotNull(result);
        assertEquals(5, result.getQuantity());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void reduceQuantity_ShouldReturnNull_WhenInsufficientQuantity() {
        // Arrange
        testProduct.setQuantity(3);
        when(productRepository.findById(testId)).thenReturn(Optional.of(testProduct));

        // Act
        Product result = productService.reduceQuantity(testId, 5);

        // Assert
        assertNull(result);
        verify(productRepository, never()).save(any(Product.class));
    }
} 