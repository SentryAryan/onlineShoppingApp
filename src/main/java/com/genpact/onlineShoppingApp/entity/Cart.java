package com.genpact.onlineShoppingApp.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;

import java.util.List;
import lombok.Data;

@Data
@Document(collection = "cart")
public class Cart {

    @Id
    private ObjectId id;

    private List<Product> products;

    private User user;
}
