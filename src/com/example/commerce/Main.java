package com.example.commerce;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        List<Product> productList = new ArrayList<Product>();

        Product product1 = new Product("Galaxy 25", 1200000, "최신 안드로이드 스마트폰",10);
        Product product2 = new Product("iphone", 1350000, "애플의 최신 스마트폰",5);
        Product product3 = new Product("MacBook Pro", 2400000, "M3 칩셋이 탑재된 노트북",3);
        Product product4 = new Product("AirPods Pro", 350000, "노이즈 캔슬링 무선 이어폰",6);

        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        productList.add(product4);

        CommerceSystem commerceSystem = new CommerceSystem(productList);
        commerceSystem.start();
    }
}
