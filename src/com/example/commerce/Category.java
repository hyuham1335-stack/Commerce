package com.example.commerce;

import java.util.ArrayList;
import java.util.List;

public class Category {

    private final String categoryName;

    // 카테고리 내 제품 목록 저장
    private final List<Product> products;

    public Category(List<Product> products, String categoryName) {

        this.products = new ArrayList<>(products);
        this.categoryName = categoryName;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    //getter

    public String getCategoryName() {
        return categoryName;
    }

    public List<Product> getProducts() {
        return products;
    }
}
