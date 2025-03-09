package com.example.chatapp.controller;

import com.example.chatapp.model.Product;
import com.example.chatapp.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);  // ✅ Proper HTTP response
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        List<Product> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/subcategory/{subcategory}")
    public ResponseEntity<List<Product>> getProductsBySubcategory(@PathVariable String subcategory) {
        List<Product> products = productService.getProductsBySubcategory(subcategory);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/new-arrivals")
    public List<Product> getNewArrivals() {
        return productService.getNewArrivals();
    }
}
