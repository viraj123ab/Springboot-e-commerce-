package com.example.chatapp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private double price;
    private List<String> images;
    private String category;
    private String subcategory;
    private int stock;
    private List<Variant> variants;
    private double avgRating;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Variant {
    private String size;
    private String color;
    private int stock;
}
