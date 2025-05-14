package com.moneyhandler.dao;

import com.moneyhandler.config.DbConfig;
import com.moneyhandler.model.IncomeModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IncomeDAO {

    // List all incomes for a user (with optional filters)
    public List<IncomeModel> getIncomesByUser(int userId, String typeFilter, Date fromDate, Date toDate) {
        List<IncomeModel> incomes = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
            "SELECT i.IncomeID, i.Amount, i.Date, it.TypeName, it.Source " +
            "FROM Income i " +
            "JOIN IncomeType it ON i.IncomeTypeID = it.IncomeTypeID " +
            "WHERE i.UserID = ?"
        );

        if (typeFilter != null && !typeFilter.equals("all")) {
            sql.append(" AND it.TypeName = ?");
        }
        if (fromDate != null) {
            sql.append(" AND i.Date >= ?");
        }
        if (toDate != null) {
            sql.append(" AND i.Date <= ?");
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
                IncomeModel income = new IncomeModel();
                income.setIncomeId(rs.getInt("incomeid"));
                income.setAmount(rs.getDouble("amount"));
                income.setDate(rs.getDate("date").toLocalDate());
                income.setTypeName(rs.getString("typename"));
                income.setSource(rs.getString("source"));
                incomes.add(income);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return incomes;
    }
    public List<IncomeModel> searchIncomes(int userId, String keyword) {
        List<IncomeModel> results = new ArrayList<>();
        String sql = "SELECT i.IncomeID, i.Amount, i.Date, it.TypeName, it.Source " +
                     "FROM Income i " +
                     "JOIN IncomeType it ON i.IncomeTypeID = it.IncomeTypeID " +
                     "WHERE i.UserID = ? AND " +
                     "(it.TypeName LIKE ? OR it.Source LIKE ? OR i.Amount LIKE ? OR i.Date LIKE ?) " +
                     "ORDER BY i.Date DESC";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            String keywordPattern = "%" + keyword + "%";
            stmt.setString(2, keywordPattern);
            stmt.setString(3, keywordPattern);
            stmt.setString(4, keywordPattern);
            stmt.setString(5, keywordPattern);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                IncomeModel income = new IncomeModel();
                income.setIncomeId(rs.getInt("incomeid"));
                income.setAmount(rs.getDouble("amount"));
                income.setDate(rs.getDate("date").toLocalDate());
                income.setTypeName(rs.getString("typename"));
                income.setSource(rs.getString("source"));
                results.add(income);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }
    
    // Add new income
    public boolean addIncome(IncomeModel income) {
        String sql = "INSERT INTO Income (UserID, IncomeTypeID, Amount, Date) VALUES (?, ?, ?, ?)";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, income.getUserId());
            stmt.setInt(2, income.getIncomeTypeId());
            stmt.setDouble(3, income.getAmount());
            stmt.setDate(4, Date.valueOf(income.getDate()));

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get a single income record
    public IncomeModel getIncomeById(int incomeId) {
        String sql = "SELECT i.IncomeID, i.UserID, i.IncomeTypeID, i.Amount, i.Date, it.TypeName, it.Source " +
                     "FROM Income i JOIN IncomeType it ON i.IncomeTypeID = it.IncomeTypeID WHERE i.IncomeID = ?";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, incomeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                IncomeModel income = new IncomeModel();
                income.setIncomeId(rs.getInt("incomeid"));
                income.setUserId(rs.getInt("userid"));
                income.setIncomeTypeId(rs.getInt("incometypeid"));
                income.setAmount(rs.getDouble("amount"));
                income.setDate(rs.getDate("date").toLocalDate());
                income.setTypeName(rs.getString("typename"));
                income.setSource(rs.getString("source"));
                return income;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Update an income
    public boolean updateIncome(IncomeModel income) {
        String sql = "UPDATE Income SET IncomeTypeID = ?, Amount = ?, Date = ? WHERE IncomeID = ?";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, income.getIncomeTypeId());
            stmt.setDouble(2, income.getAmount());
            stmt.setDate(3, Date.valueOf(income.getDate()));
            stmt.setInt(4, income.getIncomeId());
            stmt.setInt(5, income.getUserId());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete an income
    
    public boolean deleteIncome(int incomeId, int userId) {
        String sql = "DELETE FROM Income WHERE IncomeID = ?";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, incomeId);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
