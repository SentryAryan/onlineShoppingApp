package com.genpact.onlineShoppingApp.entity;

import org.springframework.data.annotation.Id;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;

import lombok.Data;

@Data
@Document(collection = "contactMessages")
@JsonPropertyOrder({ "id", "name", "email", "subject", "message" })
public class ContactMessage {
    
    @Id
    @JsonIgnore
    private ObjectId id;
    
    @JsonProperty("id")
    public String getId() {
        return id != null ? id.toHexString() : null;
    }
    
    private String name;
    private String email;
    private String subject;
    private String message;
}

