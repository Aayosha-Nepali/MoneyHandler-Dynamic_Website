package com.moneyhandler.model;

/**
 * Model class for Income and Expense Categories.
 */
public class CategoryModel {
    private int categoryId;
    private String categoryName;
    private String categoryType; // "income" or "expense"

    public CategoryModel() {}

    public CategoryModel(int categoryId, String categoryName, String categoryType) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }
}
