package com.genpact.onlineShoppingApp.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@Document(collection = "products")
@JsonPropertyOrder({ "id", "name", "description", "price", "imageUrl" })
public class Product {
    @Id
    @JsonIgnore
    private ObjectId id;

    @JsonProperty("id")
    public String getId() {
        return id != null ? id.toHexString() : null;
    }

    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private int quantity;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}