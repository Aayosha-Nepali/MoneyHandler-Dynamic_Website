package com.moneyhandler.util;

public class AdminPasswordGenerator {
    public static void main(String[] args) {
        String email = "aalu@gmail.com";
        String plainPassword = "Aalu@123"; //password

        String encrypted = PasswordUtil.encrypt(email, plainPassword);
        System.out.println("Encrypted Password: " + encrypted);
    }
}
