package com.moneyhandler.util;

public class AdminPasswordGenerator {
    public static void main(String[] args) {
        String username = "admin";
        String plainPassword = "MoneyHandlerAdmin@123"; //password

        String encrypted = PasswordUtil.encrypt(username, plainPassword);
        System.out.println("Encrypted Password: " + encrypted);
    }
}
