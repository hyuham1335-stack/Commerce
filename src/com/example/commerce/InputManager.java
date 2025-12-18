package com.example.commerce;

import java.util.Scanner;

public class InputManager {
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
