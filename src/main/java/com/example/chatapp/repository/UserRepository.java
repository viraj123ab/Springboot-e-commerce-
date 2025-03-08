package com.example.chatapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;
import com.example.chatapp.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    boolean existsByEmail(String email);
}
