package com.example.chatapp.repository;

import com.example.chatapp.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ProductRepository extends MongoRepository<Product, String> {

    // ✅ Case-insensitive category search with pagination
    @Query("{ 'category': { $regex: ?0, $options: 'i' } }")
    Page<Product> findByCategoryIgnoreCase(String category, Pageable pageable);

    // ✅ Case-insensitive subcategory search with pagination
    @Query("{ 'subcategory': { $regex: ?0, $options: 'i' } }")
    Page<Product> findBySubcategoryIgnoreCase(String subcategory, Pageable pageable);

    // ✅ New arrivals with pagination
    Page<Product> findByOrderByCreatedAtDesc(Pageable pageable);
}
