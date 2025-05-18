package com.moneyhandler.dao;

import com.moneyhandler.config.DbConfig;
import com.moneyhandler.model.CategoryModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for managing income and expense categories.
 */
public class CategoryDAO {

    // Fetch categories by type
    public List<CategoryModel> getCategoriesByType(String type) {
        List<CategoryModel> categories = new ArrayList<>();
        String sql = "SELECT * FROM Category WHERE category_type = ?";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, type);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CategoryModel category = new CategoryModel();
                category.setCategoryId(rs.getInt("category_id"));
                category.setCategoryName(rs.getString("category_name"));
                category.setCategoryType(rs.getString("category_type"));
                categories.add(category);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return categories;
    }

    // Add new category
    public boolean addCategory(String type, String name) {
        String sql = "INSERT INTO Category (category_type, category_name) VALUES (?, ?)";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, type);
            stmt.setString(2, name);

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Update category name
    public boolean updateCategoryName(int categoryId, String newName) {
        String sql = "UPDATE Category SET category_name = ? WHERE category_id = ?";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newName);
            stmt.setInt(2, categoryId);

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Delete category
    public boolean deleteCategory(int categoryId) {
        String sql = "DELETE FROM Category WHERE category_id = ?";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
