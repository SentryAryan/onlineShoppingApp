package com.genpact.onlineShoppingApp.service;

import org.springframework.stereotype.Service;

import org.bson.types.ObjectId;


import java.util.List;


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
    public void deleteUser(ObjectId id) {
        userRepository.deleteById(id);
    }


 
   
   public List<User> getAllUsers() {
     return userRepository.findAll();
   }
     

    

}
