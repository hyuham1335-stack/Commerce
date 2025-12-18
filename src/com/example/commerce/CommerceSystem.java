package com.example.commerce;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CommerceSystem {

    private final Scanner sc;
    private static String password = "admin123";
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
            int cartOption = 0;
            int orderedOption = 0;

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
                cartOption = optionCount;
            }

            if(!orderedItems.isEmpty()) {
                System.out.println(++optionCount + ". 주문 취소       | 진행중인 주문을 취소합니다.");
                orderedOption = optionCount;
            }

            System.out.println(++optionCount + ". 관리자 모드");

            // 입력한 번호값 반환
            int selected = getIntegerInput(sc, 0, optionCount);

            if (selected == 0) {
                System.out.println("커머스 플랫폼을 종료합니다.");
                return;
            } else if (selected > 0 && selected <= categories.size()) {
                showCategoryFilterMenu(sc, categories.get(selected - 1));
            } else if (selected == cartOption) {
                checkShoppingCart();
            } else if (selected == orderedOption) {
                cancelOrder();
            } else {
                checkAdmin(sc);
            }
        }
    }

    // 관리자 비밀번호 확인
    private void checkAdmin(Scanner sc) {

        int failureNum = 0;
        System.out.println();
        System.out.println("관리자 비밀번호를 입력해주세요:");

        while (true) {
            String inputPassword = sc.nextLine();

            if(inputPassword.equals(password)) {
                showAdminMenu(sc);
                return;
            } else {
                failureNum++;

                if (failureNum == 3) {
                    System.out.println("비밀번호 입력을 3회 실패하였습니다. 메인메뉴로 돌아갑니다.");
                    return;
                }
                System.out.println("올바른 비밀번호를 입력해주세요. 틀린 횟수: " + failureNum + "회" );
            }
        }
    }

    // 관리자 메뉴 출력
    public void showAdminMenu(Scanner sc) {
        while (true) {
            System.out.println();
            System.out.println("[ 관리자 모드 ]");
            System.out.println("1. 상품 추가");
            System.out.println("2. 상품 수정");
            System.out.println("3. 상품 삭제");
            System.out.println("4. 전체 상품 현황");
            System.out.println("0. 메인으로 돌아가기");

            int selected = getIntegerInput(sc, 0, 4);

            switch (selected) {
                case 0 -> {
                    System.out.println("메인으로 돌아갑니다.\n");
                    return;
                }
                case 1 -> selectCategory(sc);
                case 2 -> inputProductNameToEdit(sc);
                case 3 -> inputProductNameToRemove(sc);
                case 4 -> showAllProducts(sc);
            }
        }
    }

    // 제품을 추가할 카테고리 선택
    public void selectCategory(Scanner sc) {
        System.out.println();
        System.out.println("어느 카테고리에 상품을 추가하시겠습니까?");
        int categoryNum = 0;

        for (Category category : Repository.getCategories()) {
            System.out.println(++categoryNum + ". " + category.getCategoryName());
        }

        int selected = getIntegerInput(sc, 1, categoryNum);
        Category selectedCategory = Repository.getCategories().get(selected - 1);
        inputProductInfo(sc, selectedCategory);
    }

    // 제품 정보 입력
    public void inputProductInfo(Scanner sc, Category selectedCategory) {
        System.out.println("[ " + selectedCategory.getCategoryName() + " 카테고리에 상품 추가 ]");
        System.out.print("상품명을 입력해주세요: ");
        String productName = sc.nextLine();

        for(Product product : selectedCategory.getProducts()) {
            if(product.getName().equals(productName)) {
                System.out.println("이미 존재하는 상품입니다.");
                return;
            }
        }

        System.out.print("가격을 입력해주세요: ");
        int price = getIntegerInput(sc, 0 , 0);

        System.out.print("상품 설명을 입력해주세요: ");
        String description = sc.nextLine();

        System.out.print("재고수량을 입력해주세요: ");
        int quantity = getIntegerInput(sc, 0 , 0);

        confirmAddProduct(sc, selectedCategory, new Product(productName, price, description, quantity));
    }

    // 제품 추가 확정 or 취소
    public void confirmAddProduct(Scanner sc, Category selectedCategory, Product product) {

        String productName = product.getName();
        int price = product.getPrice();
        String description = product.getDescription();
        int quantity = product.getStockQuantity();

        System.out.println();
        System.out.printf("%s | %,d원 | %s | 재고: %d개\n", productName, price, description, quantity);
        System.out.println("위 정보로 상품을 추가하시겠습니까?");
        System.out.println("1. 확인    2. 취소");
        int selected = getIntegerInput(sc, 1, 2);
        switch (selected) {
            case 1 -> {
                selectedCategory.addProduct(new Product(productName, price, description, quantity));
                System.out.println("상품이 성공적으로 추가되었습니다!");
            }
            case 2 -> System.out.println("상품 추가를 취소하였습니다.");
        }
    }

    // 수정할 상품명 입력받는 메서드
    public void inputProductNameToEdit(Scanner sc) {
        System.out.println();
        System.out.print("수정할 상품명을 입력해주세요: ");
        String inputProductName = sc.nextLine();

        for (Category category : Repository.getCategories()) {
            for (Product product : category.getProducts()) {
                if (product.getName().equals(inputProductName)) {
                    System.out.printf("현재 상품 정보: %s | %,d원 | %s | 재고: %d개", product.getName(), product.getPrice(), product.getDescription(), product.getStockQuantity());
                    chooseOptionToEdit(sc, product);
                    return;
                }
            }
        }
        System.out.println("해당 상품이 존재하지 않습니다.");
    }

    // 선택한 상품에서 수정할 필드를 선택하는 메서드
    public void chooseOptionToEdit(Scanner sc, Product product) {
        System.out.println();
        System.out.println("수정할 항목을 선택해주세요:");
        System.out.println("1. 가격");
        System.out.println("2. 설명");
        System.out.println("3. 재고수량");

        int selected = getIntegerInput(sc, 1, 3);
        inputDataToEdit(sc, product, selected);
    }

    // 수정할 데이터를 입력하는 메서드
    public void inputDataToEdit(Scanner sc, Product product, int selectedOption) {
        switch (selectedOption) {
            case 1 -> {
                System.out.printf("현재 가격: %,d원\n", product.getPrice());
                System.out.print("새로운 가격을 입력해주세요: ");
                int newPrice = getIntegerInput(sc, 0 , 0);
                System.out.printf("%s의 가격이 %,d원 -> %,d원으로 수정되었습니다.\n", product.getName(), product.getPrice(), newPrice);
                product.editPrice(newPrice);
            }

            case 2 -> {
                System.out.printf("현재 설명: %s\n", product.getDescription());
                System.out.print("새로운 설명을 입력해주세요: ");
                String newDescription = sc.nextLine();
                System.out.printf("%s의 설명이 %s -> %s 로 수정되었습니다.\n", product.getName(), product.getDescription(), newDescription);
                product.editDescription(newDescription);
            }

            case 3 -> {
                System.out.printf("현재 재고: %d개\n", product.getStockQuantity());
                System.out.print("새로운 재고량을 입력해주세요: ");
                int newStockQuantity = getIntegerInput(sc, 0 , 0);
                System.out.printf("%s의 재고가 %d개 -> %d개로 수정되었습니다.\n", product.getName(), product.getStockQuantity(), newStockQuantity);
                product.editStockQuantity(newStockQuantity);
            }
        }
    }

    // 삭제할 상품명을 입력받는 메서드
    public void inputProductNameToRemove(Scanner sc) {
        System.out.println();
        System.out.print("삭제할 상품명을 입력해주세요: ");
        String inputProductName = sc.nextLine();

        for (Category category : Repository.getCategories()) {
            for (Product product : category.getProducts()) {
                if (product.getName().equals(inputProductName)) {
                    System.out.printf("현재 상품 정보: %s | %,d원 | %s | 재고: %d개", product.getName(), product.getPrice(), product.getDescription(), product.getStockQuantity());
                    confirmRemoveProduct(sc, category, product);
                    return;
                }
            }
        }
        System.out.println("해당 상품이 존재하지 않습니다.");
    }

    // 상품 삭제를 확인 후 삭제하는 메서드
    public void confirmRemoveProduct(Scanner sc, Category category, Product product) {
        System.out.println();
        System.out.println("해당 상품을 삭제하시겠습니까?");
        System.out.println("1. 확인    2. 취소");
        int selected = getIntegerInput(sc, 1, 2);

        switch (selected) {
            case 1 -> {
                category.getProducts().remove(product);
                cartItems.remove(product);
                orderedItems.remove(product);
                System.out.println("상품 삭제가 완료되었습니다.");
            }

            case 2 -> {
                System.out.println("상품 삭제가 취소되었습니다.");
            }
        }
    }

    // 등록된 모든 상품 출력
    public void showAllProducts(Scanner sc) {
        System.out.println("[ 전체 상품 목록 ]");
        for (Category category : Repository.getCategories()) {
            for (Product product : category.getProducts()) {
                System.out.printf("%s | %s | %,d원 | %s | 재고: %d개\n", category.getCategoryName(), product.getName(), product.getPrice(), product.getDescription(), product.getStockQuantity());
            }
        }
    }

    // 카테고리 내 상품을 출력하기 위한 필터를 선택하는 메뉴
    public void showCategoryFilterMenu(Scanner sc, Category category) {

        while (true) {
            System.out.println("[ " + category.getCategoryName() + " 카테고리 ]");
            System.out.println("1. 전체 상품 보기");
            System.out.println("2. 가격대별 필터링 (100만원 이하)");
            System.out.println("3. 가격대별 필터링 (100만원 초과)");
            System.out.println("0. 뒤로가기");

            int selected = getIntegerInput(sc, 0, 3);

            switch (selected) {
                case 0 -> {return;}
                case 1 -> showAllProductsInCategory(sc, category);
                case 2 -> showProductsByPrice(sc, category, 1000000, false);
                case 3 -> showProductsByPrice(sc, category, 1000000, true);
            }
        }
    }

    public void showProductsByPrice(Scanner sc, Category category, int filterPrice, Boolean isUp) {

        List<Product> list = new ArrayList<>();

        while (true) {
            int i = 0;

            if (isUp) {

                list = category.getProducts().stream()
                        .filter(product -> product.getPrice() > filterPrice)
                        .toList();

                if(list.isEmpty()) {
                    System.out.printf("%,d원을 초과하는 상품이 없습니다.\n",filterPrice);
                    return;
                }

                System.out.printf("[ %,d원 초과 상품 목록 ]\n", filterPrice);
                for (Product product : list) {
                    System.out.printf("%d. %-15s| %,11d원 | %-20s | %4d개 \n", ++i, product.getName(), product.getPrice(), product.getDescription(), product.getStockQuantity());
                }
            } else {

                list = category.getProducts().stream()
                        .filter(product -> product.getPrice() <= filterPrice)
                        .toList();

                if(list.isEmpty()) {
                    System.out.printf("%,d원 이하의 상품이 없습니다.\n",filterPrice);
                    return;
                }

                System.out.printf("[ %,d원 이하 상품 목록 ]\n", filterPrice);
                for (Product product : list) {
                    System.out.printf("%d. %-15s| %,11d원 | %-20s | %4d개 \n", ++i, product.getName(), product.getPrice(), product.getDescription(), product.getStockQuantity());
                }
            }



            System.out.println("0. 뒤로가기");

            if (selectProductForCart(sc, list) == 0) {
                return;
            }
        }
    }

    // 카테고리 내 제품 리스트 출력
    public void showAllProductsInCategory(Scanner sc, Category category) {

        if (category.getProducts().isEmpty()) {
            System.out.println("해당하는 제품이 존재하지 않습니다.\n");
            return;
        }

        while (true) {
            int i = 0;

            // 메뉴 리스트
            System.out.println("[ " + category.getCategoryName() + " 카테고리 ]");
            for (Product product : category.getProducts()) {
                System.out.printf("%d. %-15s| %,11d원 | %-20s | %4d개 \n", ++i,product.getName(),product.getPrice(), product.getDescription(), product.getStockQuantity());
            }
            System.out.println("0. 뒤로가기");

            if(selectProductForCart(sc, category.getProducts()) == 0){
                return;
            }
        }
    }

    // 장바구니에 넣을 상품 선택
    public int selectProductForCart(Scanner sc, List<Product> products) {

        int selected = getIntegerInput(sc, 0, products.size());

        if (selected != 0) {
            Product selectedProduct = products.get(selected - 1);
            System.out.println("선택한 상품: " + selectedProduct.getName() + " | " + selectedProduct.getPrice() + " | " + selectedProduct.getDescription() + " | 재고: " + selectedProduct.getStockQuantity());
            addShoppingCart(sc, selectedProduct);
        }
        return selected;
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
    // min == max 일 경우 범위 제한 없이 양의 정수를 입력받아 반환
    public static int getIntegerInput(Scanner sc, int min, int max) {

        while (true) {
            try {
                int num = Integer.parseInt(sc.nextLine());

                if (min == max) {
                    if (num <= 0) {
                        System.out.println("양의 정수를 입력해주세요");
                        continue;
                    }
                    return num;
                }

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
