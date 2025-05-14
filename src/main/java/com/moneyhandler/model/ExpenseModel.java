package com.moneyhandler.model;

import java.time.LocalDate;

/**
 * Model class for Expense entity.
 */
public class ExpenseModel {

    private int expenseId;
    private int userId;
    private int expenseTypeId;
    private double amount;
    private LocalDate date;

    // Optional display fields (for joins)
    private String typeName;
    private String vendor;

    public ExpenseModel() {}

    public ExpenseModel(int expenseId, int userId, int expenseTypeId, double amount, LocalDate date) {
        this.expenseId = expenseId;
        this.userId = userId;
        this.expenseTypeId = expenseTypeId;
        this.amount = amount;
        this.date = date;
    }

    // Getters and Setters

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getExpenseTypeId() {
        return expenseTypeId;
    }

    public void setExpenseTypeId(int expenseTypeId) {
        this.expenseTypeId = expenseTypeId;
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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
}
