package com.genpact.onlineShoppingApp.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@Document(collection = "users")
@JsonPropertyOrder({ "id", "name", "email", "password", "role" })
public class User {
    @Id
    @JsonIgnore
    private ObjectId id;

    @JsonProperty("id")
    public String getId() {
        return id != null ? id.toHexString() : null;
    }

    private String name;
    @Indexed(unique = true)
    private String email;
    private String password;
    private String role;
}
