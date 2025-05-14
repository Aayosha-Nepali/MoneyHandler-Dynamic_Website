package com.moneyhandler.dao;

import com.moneyhandler.config.DbConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * AdminDAO provides database access methods for administrative tasks like counting users, incomes, and expenses.
 */
public class AdminDAO {

    /**
     * Get total number of registered users.
     */
    public int getTotalUserCount() {
        String sql = "SELECT COUNT(*) FROM User";
        return executeCountQuery(sql);
    }

    /**
     * Get total number of income records.
     */
    public int getTotalIncomeRecordCount() {
        String sql = "SELECT COUNT(*) FROM Income";
        return executeCountQuery(sql);
    }

    /**
     * Get total number of expense records.
     */
    public int getTotalExpenseRecordCount() {
        String sql = "SELECT COUNT(*) FROM Expense";
        return executeCountQuery(sql);
    }

    /**
     * Common method to execute count queries.
     */
    private int executeCountQuery(String sql) {
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
