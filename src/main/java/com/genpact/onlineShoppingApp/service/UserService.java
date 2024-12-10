package com.genpact.onlineShoppingApp.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.genpact.onlineShoppingApp.repository.UserRepository;
import com.genpact.onlineShoppingApp.entity.User;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public User createUser() {
        return userRepository.findAll();
    }

}
