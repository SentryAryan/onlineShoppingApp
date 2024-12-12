package com.genpact.onlineShoppingApp.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.genpact.onlineShoppingApp.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

    void deleteById(ObjectId id);

    Optional<User> findById(ObjectId id);

    // find by username
    Optional<User> findByName(String name);

    Optional<User> findByEmail(String email);
}
