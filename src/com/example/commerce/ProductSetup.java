package com.example.commerce;

import java.util.ArrayList;
import java.util.List;

public class ProductSetup {

    // 상품 정보 초기화
    public static void init(){

        List<Product> productList1 = new ArrayList<Product>();
        List<Product> productList2 = new ArrayList<Product>();
        List<Product> productList3 = new ArrayList<Product>();
        List<Product> productList4 = new ArrayList<Product>();

        Product product1 = new Product("Galaxy 25", 1200000, "최신 안드로이드 스마트폰",10);
        Product product2 = new Product("iphone", 1350000, "애플의 최신 스마트폰",5);
        Product product3 = new Product("MacBook Pro", 2400000, "M3 칩셋이 탑재된 노트북",3);
        Product product4 = new Product("AirPods Pro", 350000, "노이즈 캔슬링 무선 이어폰",6);

        productList1.add(product1);
        productList1.add(product2);
        productList1.add(product3);
        productList1.add(product4);

        Category category1 = new Category(productList1, "전자제품");
        Repository.addCategory(category1);


        Product product5 = new Product("청바지", 60000, "와이드 핏 청바지",10);
        Product product6 = new Product("맨투맨", 30000, "오버사이즈 맨투맨",5);
        Product product7 = new Product("패딩점퍼", 120000, "오리털 패딩점퍼",3);
        Product product8 = new Product("면바지", 20000, "부드러운 소재의 면바지",6);

        productList2.add(product5);
        productList2.add(product6);
        productList2.add(product7);
        productList2.add(product8);

        Category category2 = new Category(productList2, "의류");
        Repository.addCategory(category2);


        Product product9 = new Product("초코파이", 4500, "오리온 초코파이 12P",70);
        Product product10 = new Product("몽쉘", 4000, "카카오 생크림 초콜릿",60);
        Product product11 = new Product("쵸코하임", 5600, "바삭바삭한 고급 초코 과자",50);
        Product product12 = new Product("초코송이", 1200, "버섯 모양의 초콜릿 과자",100);

        productList3.add(product9);
        productList3.add(product10);
        productList3.add(product11);
        productList3.add(product12);

        Category category3 = new Category(productList3, "식품");
        Repository.addCategory(category3);
    }
}
