package com.example.commerce;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        ProductSetup.init();
        Scanner sc = new Scanner(System.in);
        CommerceSystem commerceSystem = new CommerceSystem(sc);
        commerceSystem.start();

        sc.close();
    }
}
