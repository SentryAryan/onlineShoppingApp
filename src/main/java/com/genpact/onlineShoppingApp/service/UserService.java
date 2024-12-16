package com.genpact.onlineShoppingApp.service;

import org.springframework.stereotype.Service;
import org.bson.types.ObjectId;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.genpact.onlineShoppingApp.repository.ProductRepository;
import com.genpact.onlineShoppingApp.repository.UserRepository;
import com.genpact.onlineShoppingApp.entity.Product;
import com.genpact.onlineShoppingApp.entity.User;
import com.genpact.onlineShoppingApp.entity.CartItem;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public User createUser(User user) {
        // Check if email already exists
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        return userRepository.save(user);
    }

    // Delete Operation
    public boolean deleteUser(ObjectId id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Update Functionality
    public User updateUser(ObjectId id, User user) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            user.setId(id);
            return userRepository.save(user);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    // find by username
    public User findByName(String name) {
        return userRepository.findByName(name).orElse(null);
    }

    // login functionality
    public User login(String email, String password) {
        User foundUser = userRepository.findByEmail(email).orElse(null);

        if (foundUser != null && foundUser.getPassword().equals(password)) {
            return foundUser;
        }
        return null;
    }

    // add to cart
    public User addToCart(ObjectId productId, ObjectId userId, int quantity) {
        User foundUser = userRepository.findById(userId).orElse(null);
        Product product = productRepository.findById(productId).orElse(null);
        CartItem cartItem = new CartItem(product, quantity);
        // check if product is already in cart
        if (foundUser != null && product != null && !foundUser.getCart().contains(cartItem)) {
            foundUser.getCart().add(cartItem);
            return userRepository.save(foundUser);
        }
        return null;
    }

    // get cart
    public List<CartItem> getCart(ObjectId userId) {
        User foundUser = userRepository.findById(userId).orElse(null);
        if (foundUser != null) {
            return foundUser.getCart();
        }
        return null;
    }

    // remove from cart
    public User removeFromCart(ObjectId productId, ObjectId userId) {

        User foundUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Remove cart item where product ID matches
        if (foundUser.getCart().removeIf(item -> item.getProduct().getId().toString().equals(productId.toString()))) {
            return userRepository.save(foundUser);
        }
        return null;

    }
}
