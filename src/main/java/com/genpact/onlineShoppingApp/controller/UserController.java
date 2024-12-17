package com.genpact.onlineShoppingApp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.genpact.onlineShoppingApp.entity.User;
import com.genpact.onlineShoppingApp.service.UserService;

import org.bson.types.ObjectId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import com.genpact.onlineShoppingApp.entity.CartItem;
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "https://online-shooping-app-frontend.vercel.app/", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST,
        RequestMethod.PUT, RequestMethod.DELETE }, allowCredentials = "true")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.ok(createdUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT) // 409 Conflict status
                    .body("User registration failed: " + e.getMessage());
        }
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Delete Operation
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable ObjectId id) {

        if (userService.deleteUser(id)) {
            return ResponseEntity.ok("User deleted successfully");
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

    // Update Functionality
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable ObjectId id, @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(id, user);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            if (e.getMessage().equals("User not found")) {
                return ResponseEntity.status(404).body("User not found");
            } else if (e.getMessage().equals("Email already exists")) {
                return ResponseEntity.status(409).body("Email already exists");
            }
            return ResponseEntity.status(409).body("User update failed: " + e.getMessage());
        }
    }

    // find by username
    @GetMapping("/username/{name}")
    public User findByUsername(@PathVariable String name) {
        return userService.findByName(name);
    }

    // login functionality
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        User user = userService.login(email, password);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(401).body("Login failed: Invalid credentials");
        }
    }

    // add to cart
    @PostMapping("/add-to-cart/{userId}/{productId}/{quantity}")
    public ResponseEntity<?> addToCart(@PathVariable ObjectId userId, @PathVariable ObjectId productId,
            @PathVariable int quantity) {
        User user = userService.addToCart(productId, userId, quantity);
        if (user != null) {
            return ResponseEntity.ok(user.getCart());
        } else {
            return ResponseEntity.status(409).body("Product already in cart");
        }
    }

    // get cart
    @GetMapping("/cart/{userId}")
    public ResponseEntity<?> getCart(@PathVariable ObjectId userId) {
        try {
            List<CartItem> cart = userService.getCart(userId);
            return ResponseEntity.ok(cart);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("User not found");
        }
    }

    // remove from cart
    @DeleteMapping("/cart/{userId}/{productId}")
    public ResponseEntity<?> removeFromCart(
            @PathVariable ObjectId userId,
            @PathVariable ObjectId productId) {
        try {
            User updatedUser = userService.removeFromCart(productId, userId);
            return ResponseEntity.ok(updatedUser.getCart());
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Product not found in cart");
        }
    }

    //empty cart
    @DeleteMapping("/empty-cart/{userId}")
    public ResponseEntity<?> emptyCart(@PathVariable ObjectId userId) {
        if (userService.emptyCart(userId)) {
            return ResponseEntity.ok("Cart emptied successfully");
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

}
