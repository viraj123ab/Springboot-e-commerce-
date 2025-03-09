package com.example.chatapp.repository;

import com.example.chatapp.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {

    @Query("{ 'category': { $regex: ?0, $options: 'i' } }")  // ✅ Case-insensitive search
    List<Product> findByCategoryIgnoreCase(String category);

    @Query("{ 'subcategory': { $regex: ?0, $options: 'i' } }")  // ✅ Case-insensitive search
    List<Product> findBySubcategoryIgnoreCase(String subcategory);

    List<Product> findTop10ByOrderByCreatedAtDesc();
}
