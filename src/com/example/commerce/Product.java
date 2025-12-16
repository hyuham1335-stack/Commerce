package com.example.commerce;

public class Product {

    private String name;
    private int price;
    private String description;
    private int stockQuantity;

    public Product(String name, int price, String description,int stockQuantity) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.stockQuantity = stockQuantity;
    }

    // 물품 구매시 수량 조절
    public void adjStockQuantity(int quantity) {
        if(stockQuantity + quantity >= 0){
            stockQuantity += quantity;
        }
    }

    // getter
    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }
}
