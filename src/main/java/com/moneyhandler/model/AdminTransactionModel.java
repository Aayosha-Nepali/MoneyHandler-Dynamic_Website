package com.moneyhandler.model;

import java.time.LocalDate;

/**
 * Model class for admin-side transaction management.
 */
public class AdminTransactionModel {
    private int transactionId;
    private int userId;
    private String username;
    private String type;     // income or expense
    private String category;
    private double amount;
    private LocalDate date;

    public AdminTransactionModel() {}

    public AdminTransactionModel(int transactionId, int userId, String username, String type, String category, double amount, LocalDate date) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.username = username;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    // Getters and Setters

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
