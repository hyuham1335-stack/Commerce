package com.example.commerce;

public enum CustomerGrade {
    BRONZE(1),
    SILVER(2),
    GOLD(3),
    PLATINUM(4);

    private final int grade;

    CustomerGrade(int grade) {
        this.grade = grade;
    }

    public static CustomerGrade getCustomerGrade(int grade) {
        for (CustomerGrade c : CustomerGrade.values()) {
            if (c.grade == grade) {
                return c;
            }
        }
        throw new IllegalArgumentException("존재하지 않는 등급입니다.");
    }

    public static int getDiscountRate(CustomerGrade grade) {
        switch (grade) {
            case BRONZE -> {return 0;}
            case SILVER -> {return 5;}
            case GOLD -> {return 10;}
            default -> {return 15;}
        }
    }
}
