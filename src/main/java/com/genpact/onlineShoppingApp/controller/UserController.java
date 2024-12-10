package com.genpact.onlineShoppingApp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @GetMapping("/path")
    public String getMethodName(@RequestParam String param) {
        return "Abhay";
    }
    

}
