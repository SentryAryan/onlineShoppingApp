package com.genpact.onlineShoppingApp.service;

import org.springframework.stereotype.Service;

import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.genpact.onlineShoppingApp.repository.UserRepository;
import com.genpact.onlineShoppingApp.entity.User;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
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

}
