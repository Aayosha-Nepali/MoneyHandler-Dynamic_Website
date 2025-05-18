package com.moneyhandler.dao;

import com.moneyhandler.config.DbConfig;
import com.moneyhandler.model.AdminTransactionModel;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class for admin-side transaction access.
 */
public class AdminTransactionDAO {

    // Get all transactions from the database (for admin view)
    public List<AdminTransactionModel> getAllTransactions() {
        List<AdminTransactionModel> transactions = new ArrayList<>();
        String sql = "SELECT t.TransactionID, t.UserID, u.Username, t.Type, t.Category, t.Amount, t.Date " +
                     "FROM Transactions t " +
                     "JOIN User u ON t.UserID = u.UserID " +
                     "ORDER BY t.Date DESC";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                AdminTransactionModel trans = new AdminTransactionModel();
                trans.setTransactionId(rs.getInt("TransactionID"));
                trans.setUserId(rs.getInt("UserID"));
                trans.setUsername(rs.getString("Username"));
                trans.setType(rs.getString("Type"));
                trans.setCategory(rs.getString("Category"));
                trans.setAmount(rs.getDouble("Amount"));
                trans.setDate(rs.getDate("Date").toLocalDate());
                transactions.add(trans);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return transactions;
    }
}
