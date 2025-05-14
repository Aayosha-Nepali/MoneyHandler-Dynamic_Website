package com.moneyhandler.model;

/**
 * Model class for expense types.
 */
public class ExpenseTypeModel {
    private int expenseTypeId;
    private String typeName;
    private String vendor;

    public ExpenseTypeModel() {}

    public ExpenseTypeModel(int expenseTypeId, String typeName, String vendor) {
        this.expenseTypeId = expenseTypeId;
        this.typeName = typeName;
        this.vendor = vendor;
    }

    // Getters and Setters
    public int getExpenseTypeId() {
        return expenseTypeId;
    }

    public void setExpenseTypeId(int expenseTypeId) {
        this.expenseTypeId = expenseTypeId;
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
