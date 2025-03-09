package com.example.chatapp.controller;

import com.example.chatapp.model.Product;
import com.example.chatapp.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // ✅ Added Pagination Support
    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Product> products = productService.getAllProducts(page, size);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    // ✅ Added Pagination Support for Categories
    @GetMapping("/category/{category}")
    public ResponseEntity<Page<Product>> getProductsByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Product> products = productService.getProductsByCategory(category, page, size);
        return ResponseEntity.ok(products);
    }

    // ✅ Added Pagination Support for Subcategories
    @GetMapping("/subcategory/{subcategory}")
    public ResponseEntity<Page<Product>> getProductsBySubcategory(
            @PathVariable String subcategory,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Product> products = productService.getProductsBySubcategory(subcategory, page, size);
        return ResponseEntity.ok(products);
    }

    // ✅ Added Pagination for New Arrivals
    @GetMapping("/new-arrivals")
    public ResponseEntity<Page<Product>> getNewArrivals(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Product> products = productService.getNewArrivals(page, size);
        return ResponseEntity.ok(products);
    }
}
