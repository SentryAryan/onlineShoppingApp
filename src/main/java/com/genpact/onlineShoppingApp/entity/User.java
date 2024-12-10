package com.genpact.onlineShoppingApp.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;

import lombok.Data;

@Data
@Document(collection = "users")
public class User {
    @Id
    private ObjectId id;
    private String name;
    private String email;
    private String password;
    private String role;
}
