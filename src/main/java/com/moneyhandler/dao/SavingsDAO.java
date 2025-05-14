package com.moneyhandler.dao;

import com.moneyhandler.config.DbConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * DAO for calculating and retrieving savings for users.
 */
public class SavingsDAO {

    // Returns monthly savings (income - expense) for the given user
    public Map<String, Double> getMonthlySavings(int userId) {
        Map<String, Double> savingsMap = new LinkedHashMap<>();

        String sql = "SELECT MONTH(date) AS month, " +
                     "SUM(CASE WHEN type = 'Income' THEN amount ELSE 0 END) AS total_income, " +
                     "SUM(CASE WHEN type = 'Expense' THEN amount ELSE 0 END) AS total_expense " +
                     "FROM transactions " +
                     "WHERE UserID = ? " +
                     "GROUP BY MONTH(date) ORDER BY MONTH(date)";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int month = rs.getInt("month");
                double income = rs.getDouble("total_income");
                double expense = rs.getDouble("total_expense");
                double saving = income - expense;

                String monthName = java.time.Month.of(month).name();
                monthName = monthName.charAt(0) + monthName.substring(1).toLowerCase();
                savingsMap.put(monthName, saving);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return savingsMap;
    }

    // Returns total savings
    public double getTotalSavings(int userId) {
        String sql = "SELECT " +
                     "SUM(CASE WHEN type = 'Income' THEN amount ELSE 0 END) AS total_income, " +
                     "SUM(CASE WHEN type = 'Expense' THEN amount ELSE 0 END) AS total_expense " +
                     "FROM transactions WHERE UserID = ?";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("total_income") - rs.getDouble("total_expense");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;
    }
}
