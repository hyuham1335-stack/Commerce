package com.example.commerce;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        List<Product> productList = new ArrayList<Product>();

        Product product1 = new Product("Galaxy 25", 1200000, "최신 안드로이드 스마트폰",10);
        Product product2 = new Product("iphone", 1350000, "애플의 최신 스마트폰",5);
        Product product3 = new Product("MacBook Pro", 2400000, "M3 칩셋이 탑재된 노트북",3);
        Product product4 = new Product("AirPods Pro", 350000, "노이즈 캔슬링 무선 이어폰",6);

        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        productList.add(product4);

        System.out.println("[ 실시간 커머스 플랫폼 - 전자제품 ]");

        int i = 0;
        for (Product product : productList) {
            System.out.printf("%d. %-15s| %,11d원 | %-20s | %4d개 \n", ++i,product.name,product.price,product.description, product.stockQuantity);
        }

        System.out.println("0. 종료            | 프로그램 종료");

        boolean isChoosen = false;
        while (!isChoosen) {
            try {
                int num = Integer.parseInt(sc.nextLine());
                if(num == 0){
                    isChoosen = true;
                    System.out.println("커머스 플랫폼을 종료합니다.");
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자 번호를 입력해 주세요");
            }
        }
    }
}
