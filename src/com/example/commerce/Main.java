package com.example.commerce;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // 상품 목록 추가 및 Repository에 카테고리 저장
        ProductSetup.init();

        Scanner sc = new Scanner(System.in);
        CommerceSystem commerceSystem = new CommerceSystem(sc);

        // 비지니스 로직 시작
        commerceSystem.start();

        sc.close();
    }
}
