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

    // 물품 주문 성공 및 주문 취소시 수량 조절 메서드
    public void adjStockQuantity(int quantity) {
        if(stockQuantity + quantity >= 0){
            stockQuantity += quantity;
        }
    }


    // 관리자 메뉴를 통한 필드 편집 메서드

    public void editPrice(int newPrice) {
        if(newPrice > 0) {
            price = newPrice;
        }
    }

    public void editDescription(String newDescription) {
        description = newDescription;
    }

    public void editStockQuantity(int newStockQuantity) {
        if(newStockQuantity >= 0) {
            stockQuantity = newStockQuantity;
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
