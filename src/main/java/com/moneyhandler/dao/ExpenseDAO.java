package com.moneyhandler.dao;

import com.moneyhandler.config.DbConfig;
import com.moneyhandler.model.ExpenseModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO {

    // Get expenses for a user with optional filters
    public List<ExpenseModel> getExpensesByUser(int userId, String typeFilter, Date fromDate, Date toDate) {
        List<ExpenseModel> expenses = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
            "SELECT e.ExpenseID, e.Amount, e.Date, et.TypeName, et.Vendor " +
            "FROM Expense e " +
            "JOIN ExpenseType et ON e.ExpenseTypeID = et.ExpenseTypeID " +
            "WHERE e.UserID = ?"
        );

        if (typeFilter != null && !typeFilter.equals("all")) {
            sql.append(" AND et.TypeName = ?");
        }
        if (fromDate != null) {
            sql.append(" AND e.Date >= ?");
        }
        if (toDate != null) {
            sql.append(" AND e.Date <= ?");
        }

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            stmt.setInt(1, userId);
            int index = 2;

            if (typeFilter != null && !typeFilter.equals("all")) {
                stmt.setString(index++, typeFilter);
            }
            if (fromDate != null) {
                stmt.setDate(index++, fromDate);
            }
            if (toDate != null) {
                stmt.setDate(index++, toDate);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ExpenseModel expense = new ExpenseModel();
                expense.setExpenseId(rs.getInt("ExpenseID"));
                expense.setAmount(rs.getDouble("Amount"));
                expense.setDate(rs.getDate("Date").toLocalDate());
                expense.setTypeName(rs.getString("TypeName"));
                expense.setVendor(rs.getString("Vendor"));
                expenses.add(expense);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return expenses;
    }

    // Add new expense
    public boolean addExpense(ExpenseModel expense) {
        String sql = "INSERT INTO Expense (UserID, ExpenseTypeID, Amount, Date) VALUES (?, ?, ?, ?)";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, expense.getUserId());
            stmt.setInt(2, expense.getExpenseTypeId());
            stmt.setDouble(3, expense.getAmount());
            stmt.setDate(4, Date.valueOf(expense.getDate()));

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get single expense
    public ExpenseModel getExpenseById(int expenseId) {
        String sql = "SELECT e.ExpenseID, e.UserID, e.ExpenseTypeID, e.Amount, e.Date, et.TypeName, et.Vendor " +
                     "FROM Expense e JOIN ExpenseType et ON e.ExpenseTypeID = et.ExpenseTypeID WHERE e.ExpenseID = ?";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, expenseId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ExpenseModel expense = new ExpenseModel();
                expense.setExpenseId(rs.getInt("ExpenseID"));
                expense.setUserId(rs.getInt("UserID"));
                expense.setExpenseTypeId(rs.getInt("ExpenseTypeID"));
                expense.setAmount(rs.getDouble("Amount"));
                expense.setDate(rs.getDate("Date").toLocalDate());
                expense.setTypeName(rs.getString("Typename"));
                expense.setVendor(rs.getString("Vendor"));
                return expense;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Update expense
    public boolean updateExpense(ExpenseModel expense) {
        String sql = "UPDATE Expense SET ExpenseTypeID = ?, Amount = ?, Date = ? WHERE ExpenseID = ?";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, expense.getExpenseTypeId());
            stmt.setDouble(2, expense.getAmount());
            stmt.setDate(3, Date.valueOf(expense.getDate()));
            stmt.setInt(4, expense.getExpenseId());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete expense
    public boolean deleteExpense(int expenseId, int userId) {
        String sql = "DELETE FROM Expense WHERE ExpenseID = ?";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, expenseId);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Search expenses
    public List<ExpenseModel> searchExpenses(int userId, String keyword) {
        List<ExpenseModel> results = new ArrayList<>();
        String sql = "SELECT e.ExpenseID, e.Amount, e.Date, et.TypeName, et.Vendor " +
                     "FROM Expense e " +
                     "JOIN ExpenseType et ON e.ExpenseTypeID = et.ExpenseTypeID " +
                     "WHERE e.UserID = ? AND " +
                     "(et.TypeName LIKE ? OR et.Vendor LIKE ? OR e.Amount LIKE ? OR e.Date LIKE ?) " +
                     "ORDER BY e.Date DESC";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            String pattern = "%" + keyword + "%";
            stmt.setString(2, pattern);
            stmt.setString(3, pattern);
            stmt.setString(4, pattern);
            stmt.setString(5, pattern);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ExpenseModel expense = new ExpenseModel();
                expense.setExpenseId(rs.getInt("ExpenseID"));
                expense.setAmount(rs.getDouble("Amount"));
                expense.setDate(rs.getDate("Date").toLocalDate());
                expense.setTypeName(rs.getString("Typename"));
                expense.setVendor(rs.getString("Vendor"));
                results.add(expense);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }
}
