package com.moneyhandler.dao;

import com.moneyhandler.config.DbConfig;
import com.moneyhandler.model.ExpenseTypeModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class for managing ExpenseType records.
 */
public class ExpenseTypeDAO {

    /**
     * Returns all expense types from the database.
     */
    public List<ExpenseTypeModel> getAllTypes() {
        List<ExpenseTypeModel> types = new ArrayList<>();

        String sql = "SELECT * FROM ExpenseType";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ExpenseTypeModel type = new ExpenseTypeModel();
                type.setExpenseTypeId(rs.getInt("ExpenseTypeID"));
                type.setTypeName(rs.getString("TypeName"));
                type.setVendor(rs.getString("Vendor"));
                types.add(type);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return types;
    }
}
