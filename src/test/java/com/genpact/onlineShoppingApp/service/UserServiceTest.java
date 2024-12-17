package com.genpact.onlineShoppingApp.service;

import com.genpact.onlineShoppingApp.entity.User;
import com.genpact.onlineShoppingApp.entity.CartItem;
import com.genpact.onlineShoppingApp.entity.Product;
import com.genpact.onlineShoppingApp.repository.UserRepository;
import com.genpact.onlineShoppingApp.repository.ProductRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private ObjectId testId;
    private Product testProduct;
    private CartItem testCartItem;

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
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        User result = userService.createUser(testUser);

        // Assert
        assertNotNull(result);
        assertEquals(testUser.getEmail(), result.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_ShouldThrowException_WhenEmailExists() {
        // Arrange
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.createUser(testUser));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        // Arrange
        when(userRepository.findAll()).thenReturn(Arrays.asList(testUser));

        // Act
        List<User> result = userService.getAllUsers();

        // Assert
        assertEquals(1, result.size());
        assertEquals(testUser, result.get(0));
    }

    @Test
    void login_ShouldReturnUser_WhenCredentialsValid() {
        // Arrange
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));

        // Act
        User result = userService.login(testUser.getEmail(), testUser.getPassword());

        // Assert
        assertNotNull(result);
        assertEquals(testUser.getEmail(), result.getEmail());
    }

    // @Test
    // void addToCart_ShouldAddProductToCart() {
    //     // Arrange
    //     when(userRepository.findById(testId)).thenReturn(Optional.of(testUser));
    //     when(productRepository.findById(testProduct.getId())).thenReturn(Optional.of(testProduct));
    //     when(userRepository.save(any(User.class))).thenReturn(testUser);

    //     // Act
    //     User result = userService.addToCart(testProduct.getId(), testId, 1);

    //     // Assert
    //     assertNotNull(result);
    //     assertFalse(result.getCart().isEmpty());
    //     verify(userRepository).save(any(User.class));
    // }

    // @Test
    // void addToCart_ShouldUpdateQuantity_WhenProductExists() {
    //     // Arrange
    //     testUser.getCart().add(testCartItem);
    //     when(userRepository.findById(testId)).thenReturn(Optional.of(testUser));
    //     when(productRepository.findById(testProduct.getId())).thenReturn(Optional.of(testProduct));
    //     when(userRepository.save(any(User.class))).thenReturn(testUser);

    //     // Act
    //     User result = userService.addToCart(testProduct.getId(), testId, 2);

    //     // Assert
    //     assertNotNull(result);
    //     assertEquals(3, result.getCart().get(0).getQuantity()); // 1 + 2 = 3
    //     verify(userRepository).save(any(User.class));
    // }

    // @Test
    // void removeFromCart_ShouldRemoveProductFromCart() {
    //     // Arrange
    //     testUser.getCart().add(testCartItem);
    //     when(userRepository.findById(testId)).thenReturn(Optional.of(testUser));
    //     when(userRepository.save(any(User.class))).thenReturn(testUser);

    //     // Act
    //     User result = userService.removeFromCart(testProduct.getId(), testId);

    //     // Assert
    //     assertNotNull(result);
    //     assertTrue(result.getCart().isEmpty());
    //     verify(userRepository).save(any(User.class));
    // }

    // @Test
    // void removeFromCart_ShouldReturnNull_WhenProductNotInCart() {
    //     // Arrange
    //     when(userRepository.findById(testId)).thenReturn(Optional.of(testUser));

    //     // Act
    //     User result = userService.removeFromCart(testProduct.getId(), testId);

    //     // Assert
    //     assertNull(result);
    //     verify(userRepository, never()).save(any(User.class));
    // }

    @Test
    void emptyCart_ShouldClearCart() {
        // Arrange
        testUser.getCart().add(testCartItem);
        when(userRepository.findById(testId)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        boolean result = userService.emptyCart(testId);

        // Assert
        assertTrue(result);
        verify(userRepository).save(any(User.class));
    }
} 