package com.genpact.onlineShoppingApp.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.genpact.onlineShoppingApp.entity.ContactMessage;

@Repository
public interface ContactMessageRepository extends MongoRepository<ContactMessage, ObjectId> {
        
}
