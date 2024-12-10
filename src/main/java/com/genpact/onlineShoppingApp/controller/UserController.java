package com.genpact.onlineShoppingApp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.genpact.onlineShoppingApp.entity.User;
import com.genpact.onlineShoppingApp.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
@Autowired
private UserService userService;

    @GetMapping("/path")
    public String getMethodName(@RequestParam String param) {
        return "Abhay";
    }

    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
    

}
