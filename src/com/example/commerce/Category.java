package com.example.commerce;

import java.util.ArrayList;
import java.util.List;

public class Category {
    public String categoryName;
    private final List<Product> products;

    public Category(List<Product> products, String categoryName) {

        this.products = new ArrayList<>(products);
        this.categoryName = categoryName;
    }

    public List<Product> getProducts() {
        return products;
    }
}
