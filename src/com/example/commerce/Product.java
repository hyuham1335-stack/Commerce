package com.example.commerce;

public class Product {

    String name;
    int price;
    String description;
    int stockQuantity;

    public Product(String name, int price, String description,int stockQuantity) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.stockQuantity = stockQuantity;
    }
}
