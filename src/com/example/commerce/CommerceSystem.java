package com.example.commerce;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommerceSystem {
    private final List<Product> products;

    public CommerceSystem(List<Product> products) {
        this.products = new ArrayList<>(products);
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        int i = 0;

        // 메뉴 리스트
        System.out.println("[ 실시간 커머스 플랫폼 - 전자제품 ]");
        for (Product product : products) {
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

        sc.close();
    }
}
