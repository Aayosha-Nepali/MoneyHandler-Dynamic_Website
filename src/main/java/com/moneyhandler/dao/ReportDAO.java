package com.moneyhandler.dao;

import com.moneyhandler.config.DbConfig;
import com.moneyhandler.model.TransactionModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for retrieving combined transaction data for reports.
 */
public class ReportDAO {

    public List<TransactionModel> getAllTransactionsForUser(int userId) {
        List<TransactionModel> transactions = new ArrayList<>();

        String incomeSql = "SELECT 'Income' AS type, it.TypeName AS category, i.Amount, i.Date " +
                "FROM Income i JOIN IncomeType it ON i.IncomeTypeID = it.IncomeTypeID WHERE i.UserID = ?";

        String expenseSql = "SELECT 'Expense' AS type, et.TypeName AS category, e.Amount, e.Date " +
                "FROM Expense e JOIN ExpenseType et ON e.ExpenseTypeID = et.ExpenseTypeID WHERE e.UserID = ?";

        String savingSql = "SELECT 'Saving' AS type, 'Saving' AS category, s.Amount, s.Date " +
                "FROM Savings s WHERE s.UserID = ?";

        try (Connection conn = DbConfig.getDbConnection()) {
            // Income
            try (PreparedStatement stmt = conn.prepareStatement(incomeSql)) {
                stmt.setInt(1, userId);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    transactions.add(new TransactionModel(
                            rs.getString("type"),
                            rs.getString("category"),
                            rs.getDouble("amount"),
                            rs.getDate("date").toLocalDate()
                    ));
                }
            }

            // Expense
            try (PreparedStatement stmt = conn.prepareStatement(expenseSql)) {
                stmt.setInt(1, userId);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    transactions.add(new TransactionModel(
                            rs.getString("type"),
                            rs.getString("category"),
                            rs.getDouble("amount"),
                            rs.getDate("date").toLocalDate()
                    ));
                }
            }

            // Savings
            try (PreparedStatement stmt = conn.prepareStatement(savingSql)) {
                stmt.setInt(1, userId);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    transactions.add(new TransactionModel(
                            rs.getString("type"),
                            rs.getString("category"),
                            rs.getDouble("amount"),
                            rs.getDate("date").toLocalDate()
                    ));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return transactions;
    }
}
