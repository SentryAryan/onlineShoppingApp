package com.genpact.onlineShoppingApp.controller;

import com.genpact.onlineShoppingApp.entity.User;
import com.genpact.onlineShoppingApp.entity.CartItem;
import com.genpact.onlineShoppingApp.entity.Product;
import com.genpact.onlineShoppingApp.service.UserService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User testUser;
    private ObjectId testId;
    private CartItem testCartItem;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        testId = new ObjectId();
        testUser = new User();
        testUser.setId(testId);
        testUser.setName("Test User");
        testUser.setEmail("test@example.com");
        testUser.setPassword("password123");
        testUser.setRole("USER");
        testUser.setCart(new ArrayList<>());

        testProduct = new Product();
        testProduct.setId(new ObjectId());
        testProduct.setName("Test Product");
        testProduct.setPrice(99.99);

        testCartItem = new CartItem(testProduct, 1);
    }

    @Test
    void createUser_ShouldReturnCreatedUser() {
        // Arrange
        when(userService.createUser(any(User.class))).thenReturn(testUser);

        // Act
        ResponseEntity<?> response = userController.createUser(testUser);

        // Assert
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(testUser, response.getBody());
    }

    @Test
    void createUser_ShouldReturnConflict_WhenEmailExists() {
        // Arrange
        when(userService.createUser(any(User.class)))
            .thenThrow(new RuntimeException("Email already exists"));

        // Act
        ResponseEntity<?> response = userController.createUser(testUser);

        // Assert
        assertEquals(409, response.getStatusCode().value());
        assertTrue(response.getBody().toString().contains("Email already exists"));
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        // Arrange
        List<User> expectedUsers = Arrays.asList(testUser);
        when(userService.getAllUsers()).thenReturn(expectedUsers);

        // Act
        List<User> actualUsers = userController.getAllUsers();

        // Assert
        assertEquals(expectedUsers, actualUsers);
        assertEquals(1, actualUsers.size());
    }

    @Test
    void deleteUser_ShouldReturnSuccess() {
        // Arrange
        when(userService.deleteUser(testId)).thenReturn(true);

        // Act
        ResponseEntity<String> response = userController.deleteUser(testId);

        // Assert
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("User deleted successfully", response.getBody());
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser() {
        // Arrange
        when(userService.updateUser(eq(testId), any(User.class))).thenReturn(testUser);

        // Act
        ResponseEntity<?> response = userController.updateUser(testId, testUser);

        // Assert
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(testUser, response.getBody());
    }

    @Test
    void login_ShouldReturnUser_WhenCredentialsValid() {
        // Arrange
        when(userService.login("test@example.com", "password123")).thenReturn(testUser);

        // Act
        ResponseEntity<?> response = userController.login("test@example.com", "password123");

        // Assert
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(testUser, response.getBody());
    }

    @Test
    void login_ShouldReturnUnauthorized_WhenCredentialsInvalid() {
        // Arrange
        when(userService.login("test@example.com", "wrongpassword")).thenReturn(null);

        // Act
        ResponseEntity<?> response = userController.login("test@example.com", "wrongpassword");

        // Assert
        assertEquals(401, response.getStatusCode().value());
        assertEquals("Login failed: Invalid credentials", response.getBody());
    }

    // @Test
    // void addToCart_ShouldReturnUpdatedCart() {
    //     // Arrange
    //     testUser.getCart().add(testCartItem);
    //     when(userService.addToCart(any(ObjectId.class), any(ObjectId.class), anyInt()))
    //         .thenReturn(testUser);

    //     // Act
    //     ResponseEntity<?> response = userController.addToCart(testId, testProduct.getId(), 1);

    //     // Assert
    //     assertTrue(response.getStatusCode().is2xxSuccessful());
    //     assertEquals(testUser.getCart(), response.getBody());
    // }

    @Test
    void getCart_ShouldReturnUserCart() {
        // Arrange
        testUser.getCart().add(testCartItem);
        when(userService.getCart(testId)).thenReturn(testUser.getCart());

        // Act
        ResponseEntity<?> response = userController.getCart(testId);

        // Assert
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(testUser.getCart(), response.getBody());
    }

    // @Test
    // void removeFromCart_ShouldReturnUpdatedCart() {
    //     // Arrange
    //     testUser.getCart().add(testCartItem);
    //     when(userService.removeFromCart(any(ObjectId.class), any(ObjectId.class)))
    //         .thenReturn(testUser);

    //     // Act
    //     ResponseEntity<?> response = userController.removeFromCart(testId, testProduct.getId());

    //     // Assert
    //     assertTrue(response.getStatusCode().is2xxSuccessful());
    //     assertEquals(testUser.getCart(), response.getBody());
    // }

    @Test
    void emptyCart_ShouldReturnSuccess() {
        // Arrange
        when(userService.emptyCart(testId)).thenReturn(true);

        // Act
        ResponseEntity<?> response = userController.emptyCart(testId);

        // Assert
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("Cart emptied successfully", response.getBody());
    }

    @Test
    void findByUsername_ShouldReturnUser() {
        // Arrange
        when(userService.findByName("Test User")).thenReturn(testUser);

        // Act
        User result = userController.findByUsername("Test User");

        // Assert
        assertNotNull(result);
        assertEquals("Test User", result.getName());
    }
} 