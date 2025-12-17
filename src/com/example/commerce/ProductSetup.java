package com.example.commerce;

import java.util.ArrayList;
import java.util.List;

public class ProductSetup {

    // 상품 정보 초기화
    public static void init(){

        Category category1 = new Category(new ArrayList<Product>(), "전자제품");
        category1.addProduct(new Product("Galaxy 25", 1200000, "최신 안드로이드 스마트폰",10));
        category1.addProduct(new Product("iphone", 1350000, "애플의 최신 스마트폰",5));
        category1.addProduct(new Product("MacBook Pro", 2400000, "M3 칩셋이 탑재된 노트북",3));
        category1.addProduct(new Product("AirPods Pro", 350000, "노이즈 캔슬링 무선 이어폰",6));
        Repository.addCategory(category1);

        Category category2 = new Category(new ArrayList<Product>(), "의류");
        category2.addProduct(new Product("청바지", 60000, "와이드 핏 청바지",10));
        category2.addProduct(new Product("맨투맨", 30000, "오버사이즈 맨투맨",5));
        category2.addProduct(new Product("패딩점퍼", 120000, "오리털 패딩점퍼",3));
        category2.addProduct(new Product("면바지", 20000, "부드러운 소재의 면바지",6));
        Repository.addCategory(category2);

        Category category3 = new Category(new ArrayList<Product>(), "식품");
        category3.addProduct(new Product("초코파이", 4500, "오리온 초코파이 12P",70));
        category3.addProduct(new Product("몽쉘", 4000, "카카오 생크림 초콜릿",60));
        category3.addProduct(new Product("쵸코하임", 5600, "바삭바삭한 고급 초코 과자",50));
        category3.addProduct(new Product("초코송이", 1200, "버섯 모양의 초콜릿 과자",100));
        Repository.addCategory(category3);
    }
}
