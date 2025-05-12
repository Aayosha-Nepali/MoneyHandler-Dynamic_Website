package com.moneyhandler.model;

import java.time.LocalDate;

/**
 * Represents a user's income entry.
 */
public class IncomeModel {
    private int incomeId;
    private int userId;
    private int incomeTypeId;
    private double amount;
    private LocalDate date;

    // For display (joined from IncomeType table)
    private String typeName;
    private String source;

    // Constructors
    public IncomeModel() {}

    public IncomeModel(int incomeId, int userId, int incomeTypeId, double amount, LocalDate date) {
        this.incomeId = incomeId;
        this.userId = userId;
        this.incomeTypeId = incomeTypeId;
        this.amount = amount;
        this.date = date;
    }

    // Getters and Setters
    public int getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(int incomeId) {
        this.incomeId = incomeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getIncomeTypeId() {
        return incomeTypeId;
    }

    public void setIncomeTypeId(int incomeTypeId) {
        this.incomeTypeId = incomeTypeId;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
