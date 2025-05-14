package com.moneyhandler.model;

/**
 * Model class for income types.
 */
public class IncomeTypeModel {
    private int incomeTypeId;
    private String typeName;
    private String source;

    public IncomeTypeModel() {}

    public IncomeTypeModel(int incomeTypeId, String typeName, String source) {
        this.incomeTypeId = incomeTypeId;
        this.typeName = typeName;
        this.source = source;
    }

    public int getIncomeTypeId() {
        return incomeTypeId;
    }

    public void setIncomeTypeId(int incomeTypeId) {
        this.incomeTypeId = incomeTypeId;
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
