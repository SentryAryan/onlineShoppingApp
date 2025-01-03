package com.genpact.onlineShoppingApp.controller;

import com.genpact.onlineShoppingApp.entity.Product;
import com.genpact.onlineShoppingApp.service.ProductService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private Cloudinary cloudinary;

    @Mock
    private Uploader uploader;

    @InjectMocks
    private ProductController productController;

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
        testProduct.setImageUrl("http://test-image.com");

        // Mock cloudinary uploader
        when(cloudinary.uploader()).thenReturn(uploader);
        Map<String, String> uploadResult = new HashMap<>();
        uploadResult.put("url", "http://test-image.com");
        try {
            when(uploader.upload(any(byte[].class), any(Map.class))).thenReturn(uploadResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getAllProducts_ShouldReturnListOfProducts() {
        // Arrange
        List<Product> expectedProducts = Arrays.asList(testProduct);
        when(productService.getAllProducts()).thenReturn(expectedProducts);

        // Act
        List<Product> actualProducts = productController.getAllProducts();

        // Assert
        assertEquals(expectedProducts, actualProducts);
        assertEquals(1, actualProducts.size());
    }

    @Test
    void getProductByName_ShouldReturnProduct() {
        // Arrange
        when(productService.getProductByName("Test Product")).thenReturn(testProduct);

        // Act
        Product result = productController.getProductByName("Test Product");

        // Assert
        assertNotNull(result);
        assertEquals("Test Product", result.getName());
    }

    // @Test
    // void addProduct_ShouldReturnCreatedProduct() throws Exception {
    //     // Arrange
    //     MockMultipartFile file = new MockMultipartFile(
    //         "file", "test.jpg", "image/jpeg", "test image content".getBytes()
    //     );
    //     when(productService.addProduct(any(Product.class))).thenReturn(testProduct);

    //     // Act
    //     ResponseEntity<Product> response = productController.addProduct(
    //         file, "Test Product", "Test Description", 99.99, 10
    //     );

    //     // Assert
    //     assertTrue(response.getStatusCode().is2xxSuccessful());
    //     assertNotNull(response.getBody());
    //     assertEquals("Test Product", response.getBody().getName());
    // }

    @Test
    void deleteProduct_ShouldReturnSuccess() {
        // Arrange
        when(productService.deleteProduct(testId)).thenReturn(true);

        // Act
        ResponseEntity<String> response = productController.deleteProduct(testId);

        // Assert
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("Product deleted successfully", response.getBody());
    }

    @Test
    void getProductById_ShouldReturnProduct() {
        // Arrange
        when(productService.getProductById(testId)).thenReturn(testProduct);

        // Act
        ResponseEntity<?> response = productController.getProductById(testId);

        // Assert
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(testProduct, response.getBody());
    }

    @Test
    void reduceQuantity_ShouldReturnUpdatedProduct() {
        // Arrange
        Product updatedProduct = new Product();
        updatedProduct.setQuantity(5);
        when(productService.reduceQuantity(testId, 5)).thenReturn(updatedProduct);

        // Act
        ResponseEntity<?> response = productController.reduceQuantity(testId, 5);

        // Assert
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(updatedProduct, response.getBody());
    }
} 