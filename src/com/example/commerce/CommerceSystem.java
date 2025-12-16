package com.example.commerce;

import java.util.*;

public class CommerceSystem {

    private final Scanner sc;
    private final Map<Product, Integer> cartItems = new HashMap<>();
    private final Map<Product, Integer> orderedItems = new HashMap<>();

    public CommerceSystem(Scanner sc) {
        this.sc = sc;
    }

    // 카테고리 리스트 출력
    public void start() {
        List<Category> categories = new ArrayList<>(Repository.getCategories());

        while (true) {
            System.out.println("[ 실시간 커머스 플랫폼 메인 ]");
            int optionCount = 0;
            for (Category category : categories) {
                System.out.printf("%d. %s\n", ++optionCount, category.getCategoryName());
            }
            System.out.println("0. 종료      | 프로그램 종료");

            if(!cartItems.isEmpty() || !orderedItems.isEmpty()) {
                System.out.println();
                System.out.println(" [주문 관리] ");
            }

            if(!cartItems.isEmpty()) {
                System.out.println(++optionCount + ". 장바구니 확인    | 장바구니를 확인 후 주문합니다.");
            }

            if(!orderedItems.isEmpty()) {
                System.out.println(++optionCount + ". 주문 취소       | 진행중인 주문을 취소합니다.");
            }

            // 입력한 번호값 반환
            int selected = getIntegerInput(sc, 0, optionCount);

            if (selected == 0) {
                System.out.println("커머스 플랫폼을 종료합니다.");
                return;
            } else if (selected > 0 && selected <= categories.size()) {
                showProducts(sc, categories.get(selected - 1));
            } else {
                // 장바구니 확인 메뉴 선택 시
                if (selected == categories.size() + 1 && !cartItems.isEmpty()) {
                    checkShoppingCart();
                } else {
                    cancelOrder();
                }
            }
        }
    }

    // 카테고리 내 제품 리스트 출력
    public void showProducts(Scanner sc, Category category) {

        while (true) {
            int i = 0;

            // 메뉴 리스트
            System.out.println("[ 실시간 커머스 플랫폼 - 전자제품 ]");
            for (Product product : category.getProducts()) {
                System.out.printf("%d. %-15s| %,11d원 | %-20s | %4d개 \n", ++i,product.getName(),product.getPrice(), product.getDescription(), product.getStockQuantity());
            }
            System.out.println("0. 종료            | 프로그램 종료");

            int selected = getIntegerInput(sc, 0, category.getProducts().size());

            if (selected != 0) {
                Product selectedProduct = category.getProducts().get(selected - 1);
                System.out.println("선택한 상품: " + selectedProduct.getName() + " | " + selectedProduct.getPrice() + " | " + selectedProduct.getDescription() + " | 재고: " + selectedProduct.getStockQuantity());
                addShoppingCart(sc, selectedProduct);
            } else {
                return;
            }
        }
    }

    // 장바구니에 추가하는 메서드
    public void addShoppingCart(Scanner sc, Product product) {
        System.out.println();
        System.out.println(product.getName() + " | " + product.getPrice() + " | " + product.getDescription());
        System.out.println("위 상품을 장바구니에 추가하시겠습니까?");
        System.out.println("1. 확인        2. 취소");

        int selected = getIntegerInput(sc, 1, 2);

        if (selected == 1) {
            if (product.getStockQuantity() <= 0) {
                System.out.println("선택하신 상품의 재고가 부족합니다.");
                System.out.println("장바구니 추가가 취소되었습니다.\n");
            } else {
                if (cartItems.containsKey(product)) {
                    cartItems.put(product, cartItems.get(product) + 1);
                } else {
                    cartItems.put(product, 1);
                }
                System.out.println(product.getName() + " 이(가) 장바구니에 추가되었습니다.\n");
            }
        } else {
            System.out.println("장바구니 추가를 취소하였습니다.\n");
        }
    }

    // 장바구니 확인 기능 담당 메서드
    public void checkShoppingCart() {
        System.out.println("아래와 같이 주문 하시겠습니까?");
        System.out.println();
        System.out.println("[ 장바구니 내역 ]");
        for (Product product : cartItems.keySet()) {
            System.out.println(product.getName() + " | " + product.getPrice() + " | " + product.getDescription() + " | 수량: " + cartItems.get(product) + "개");
        }
        System.out.println();
        System.out.println("[ 총 주문 금액 ]");

        int totalPrice = cartItems.keySet().stream()
                .mapToInt(product -> product.getPrice() * cartItems.get(product))
                .sum();

        System.out.println(totalPrice);
        System.out.println();
        System.out.println("1. 주문 확정      2. 메인으로 돌아가기 ");

        int selected = getIntegerInput(sc, 1, 2);
        if (selected == 1) {
            System.out.println();

            Iterator<Map.Entry<Product, Integer>> it = cartItems.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Product, Integer> entry = it.next();
                Product product = entry.getKey();
                Integer cartItemCount = entry.getValue();

                if(product.getStockQuantity() < cartItemCount) {
                    System.out.println("재고가 부족하여 "+ product.getName() + " 상품 구매를 실패하였습니다.");
                } else {
                    product.adjStockQuantity(-cartItemCount);
                    System.out.println(product.getName() + " 재고가 " + (product.getStockQuantity() + cartItemCount) +"개 -> " + product.getStockQuantity() + "개로 업데이트되었습니다.");
                    it.remove();
                    orderedItems.put(product, cartItemCount);
                }
            }

            int leftPrice = cartItems.keySet().stream()
                        .mapToInt(product -> product.getPrice() * cartItems.get(product))
                        .sum();

            if(leftPrice == totalPrice) {
                System.out.println("주문을 실패하였습니다.\n");
            } else {
                System.out.println("주문이 완료되었습니다! 총 금액: " + (totalPrice - leftPrice) + "원\n");

            }
        } else {
            System.out.println("메인으로 돌아갑니다.\n");
        }
    }

    public void cancelOrder() {
        System.out.println();
        System.out.println("[ 주문 완료 내역 ]");
        for (Product product : orderedItems.keySet()) {
            System.out.println(product.getName() + " | " + product.getPrice() + " | " + product.getDescription() + " | 수량: " + orderedItems.get(product) + "개");
        }
        System.out.println();
        System.out.println("[ 총 결제 금액 ]");

        int totalPrice = orderedItems.keySet().stream()
                .mapToInt(product -> product.getPrice() * orderedItems.get(product))
                .sum();

        System.out.println(totalPrice);
        System.out.println();
        System.out.println("1. 주문 취소      2. 메인으로 돌아가기 ");

        int selected = getIntegerInput(sc, 1, 2);

        if (selected == 1) {
            if (orderedItems.isEmpty()) {
                System.out.println("취소할 주문이 없습니다.\n");
            } else {
                System.out.println();
                for(Product product : orderedItems.keySet()) {
                    product.adjStockQuantity(orderedItems.get(product));
                }
                orderedItems.clear();
                System.out.println("주문을 취소하였습니다.\n");
            }
        } else {
            System.out.println("메인으로 돌아갑니다.\n");
        }
    }

    // 정수값 입력을 반환하는 메서드
    public static int getIntegerInput(Scanner sc, int min, int max) {

        while (true) {
            try {
                int num = Integer.parseInt(sc.nextLine());
                if(num > max || num < min) {
                    System.out.println(min + " ~ " + max + " 사이의 정수를 입력해 주세요");
                } else {
                    return num;
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자 번호를 입력해 주세요");
            }
        }
    }

}
