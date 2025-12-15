package com.example.commerce;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommerceSystem {

    private final Scanner sc;

    public CommerceSystem(Scanner sc) {
        this.sc = sc;
    }

    // 카테고리 리스트 출력
    public void start() {
        List<Category> categories = new ArrayList<>(Repository.getCategories());

        while (true) {
            System.out.println("[ 실시간 커머스 플랫폼 메인 ]");
            int i = 0;
            for (Category category : categories) {
                System.out.printf("%d. %s\n", ++i, category.categoryName);
            }
            System.out.println("0. 종료      | 프로그램 종료");

            int selected = getIntegerInput(categories.size());

            if (selected == 0) {
                System.out.println("커머스 플랫폼을 종료합니다.");
                return;
            } else {
                showProducts(categories.get(selected - 1));
            }
        }
    }

    // 카테고리 내 제품 리스트 출력
    public static void showProducts(Category category) {

        int i = 0;

        // 메뉴 리스트
        System.out.println("[ 실시간 커머스 플랫폼 - 전자제품 ]");
        for (Product product : category.getProducts()) {
            System.out.printf("%d. %-15s| %,11d원 | %-20s | %4d개 \n", ++i,product.name,product.price,product.description, product.stockQuantity);
        }
        System.out.println("0. 종료            | 프로그램 종료");

        while (true) {
            int selected = getIntegerInput(category.getProducts().size());

            if (selected != 0) {
                Product selectedProduct = category.getProducts().get(selected - 1);
                System.out.println("선택한 상품: " + selectedProduct.name + " | " + selectedProduct.price + " | " + selectedProduct.description + " | 재고: " + selectedProduct.stockQuantity);
            } else {
                return;
            }
        }
    }

    public static int getIntegerInput(int max) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                int num = Integer.parseInt(sc.nextLine());
                if(num > max || num < 0) {
                    System.out.println( "0 ~ " + max + " 사이의 정수를 입력해 주세요");
                } else {
                    return num;
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자 번호를 입력해 주세요");
            }
        }
    }
}
