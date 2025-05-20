package com.moneyhandler.dao;

import com.moneyhandler.config.DbConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Month;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * DAO to handle income and expense aggregation.
 */
public class TransactionDAO {

    /**
     * Returns monthly total income for the user, keyed by month name.
     */
    public Map<String, Double> getMonthlyIncomeData(int userId) {
        String sql = "SELECT MONTH(date) AS month, SUM(amount) AS total " +
                     "FROM transactions " +
                     "WHERE user_id = ? AND type = 'income' " +
                     "GROUP BY MONTH(date) ORDER BY MONTH(date)";
        return getMonthlyTotals(userId, sql);
    }

    /**
     * Returns monthly total expense for the user, keyed by month name.
     */
    public Map<String, Double> getMonthlyExpenseData(int userId) {
        String sql = "SELECT MONTH(date) AS month, SUM(amount) AS total " +
                     "FROM transactions " +
                     "WHERE user_id = ? AND type = 'expense' " +
                     "GROUP BY MONTH(date) ORDER BY MONTH(date)";
        return getMonthlyTotals(userId, sql);
    }

    /**
     *  method to execute monthly aggregation queries.
     */
    private Map<String, Double> getMonthlyTotals(int userId, String sql) {
        Map<String, Double> monthlyTotals = new LinkedHashMap<>();
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int monthNumber = rs.getInt("month");
                double total = rs.getDouble("total");
                String monthName = Month.of(monthNumber).name(); // e.g., JANUARY
                // Capitalize properly (Jan, Feb, ...)
                monthName = monthName.charAt(0) + monthName.substring(1).toLowerCase();
                monthlyTotals.put(monthName, total);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return monthlyTotals;
    }
    
    public double getTotalAmount(int userId, String type) {
        String sql = "SELECT SUM(amount) FROM transactions WHERE user_id = ? AND type = ?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, type);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getDouble(1) : 0.0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }
    public Map<String, Double> getGlobalMonthlyIncome() {
        String sql = "SELECT MONTH(date) AS month, SUM(amount) AS total " +
                     "FROM transactions WHERE type = 'income' " +
                     "GROUP BY MONTH(date) ORDER BY MONTH(date)";
        return getMonthlyTotals(sql);
    }

    public Map<String, Double> getGlobalMonthlyExpense() {
        String sql = "SELECT MONTH(date) AS month, SUM(amount) AS total " +
                     "FROM transactions WHERE type = 'expense' " +
                     "GROUP BY MONTH(date) ORDER BY MONTH(date)";
        return getMonthlyTotals(sql);
    }

    // Overloaded helper
    private Map<String, Double> getMonthlyTotals(String sql) {
        Map<String, Double> monthlyTotals = new LinkedHashMap<>();
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int monthNumber = rs.getInt("month");
                double total = rs.getDouble("total");
                String monthName = Month.of(monthNumber).name(); // e.g., JANUARY
                monthName = monthName.charAt(0) + monthName.substring(1).toLowerCase(); // Jan
                monthlyTotals.put(monthName, total);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return monthlyTotals;
    }


}
