package com.moneyhandler.dao;

import com.moneyhandler.config.DbConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Month;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * DAO to support admin dashboard statistics and global transaction trends.
 */
public class AdminDAO {

    // Get total number of registered users
    public int getTotalUserCount() {
        String sql = "SELECT COUNT(*) FROM User";
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

    // Get total number of income records
    public int getTotalIncomeRecordCount() {
        String sql = "SELECT COUNT(*) FROM Income";
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

    // Get total number of expense records
    public int getTotalExpenseRecordCount() {
        String sql = "SELECT COUNT(*) FROM Expense";
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

    // Monthly global income (across all users)
    public Map<String, Double> getMonthlyIncomeTotals() {
        String sql = "SELECT MONTH(Date) AS month, SUM(Amount) AS total FROM Income GROUP BY MONTH(Date) ORDER BY MONTH(Date)";
        return getMonthlyTotals(sql);
    }

    // Monthly global expense (across all users)
    public Map<String, Double> getMonthlyExpenseTotals() {
        String sql = "SELECT MONTH(Date) AS month, SUM(Amount) AS total FROM Expense GROUP BY MONTH(Date) ORDER BY MONTH(Date)";
        return getMonthlyTotals(sql);
    }

    // Shared method to convert monthly data
    private Map<String, Double> getMonthlyTotals(String sql) {
        Map<String, Double> totals = new LinkedHashMap<>();
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int monthNumber = rs.getInt("month");
                double total = rs.getDouble("total");

                String monthName = Month.of(monthNumber).name(); // e.g., JANUARY
                monthName = monthName.charAt(0) + monthName.substring(1).toLowerCase(); // Jan, Feb...
                totals.put(monthName, total);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return totals;
    }
}
