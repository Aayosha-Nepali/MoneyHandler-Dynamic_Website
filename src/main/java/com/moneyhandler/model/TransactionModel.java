package com.moneyhandler.model;

import java.time.LocalDate;

/**
 * Represents a single financial transaction entry (income, expense, or saving).
 */
public class TransactionModel {
    private String type;       // Income, Expense, Saving
    private String category;   // TypeName or "Saving"
    private double amount;
    private LocalDate date;

    public TransactionModel() {}

    public TransactionModel(String type, String category, double amount, LocalDate date) {
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    // Getters and setters
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
