package com.moneyhandler.dao;

import com.moneyhandler.config.DbConfig;
import com.moneyhandler.model.IncomeTypeModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for managing income and expense categories.
 */
public class CategoryDAO {

    // Get all income categories
    public List<IncomeTypeModel> getAllIncomeCategories() {
        List<IncomeTypeModel> list = new ArrayList<>();
        String sql = "SELECT * FROM IncomeType";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                IncomeTypeModel model = new IncomeTypeModel();
                model.setIncomeTypeId(rs.getInt("IncomeTypeID"));
                model.setTypeName(rs.getString("TypeName"));
                model.setSource(rs.getString("Source"));
                list.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Add a new income category
    public boolean addIncomeCategory(IncomeTypeModel type) {
        String sql = "INSERT INTO IncomeType (TypeName, Source) VALUES (?, ?)";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, type.getTypeName());
            stmt.setString(2, type.getSource());
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update an income category
    public boolean updateIncomeCategory(IncomeTypeModel type) {
        String sql = "UPDATE IncomeType SET TypeName = ?, Source = ? WHERE IncomeTypeID = ?";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, type.getTypeName());
            stmt.setString(2, type.getSource());
            stmt.setInt(3, type.getIncomeTypeId());
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete an income category
    public boolean deleteIncomeCategory(int typeId) {
        String sql = "DELETE FROM IncomeType WHERE IncomeTypeID = ?";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, typeId);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
