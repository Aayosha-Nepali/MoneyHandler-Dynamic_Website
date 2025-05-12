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
            "SELECT i.incomeid, i.amount, i.date, it.typename, it.source " +
            "FROM income i " +
            "JOIN incometype it ON i.incometypeid = it.incometypeid " +
            "WHERE i.userid = ?"
        );

        if (typeFilter != null && !typeFilter.equals("all")) {
            sql.append(" AND it.typename = ?");
        }
        if (fromDate != null) {
            sql.append(" AND i.date >= ?");
        }
        if (toDate != null) {
            sql.append(" AND i.date <= ?");
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

    // Add new income
    public boolean addIncome(IncomeModel income) {
        String sql = "INSERT INTO income (userid, incometypeid, amount, date) VALUES (?, ?, ?, ?)";

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
        String sql = "SELECT i.incomeid, i.userid, i.incometypeid, i.amount, i.date, it.typename, it.source " +
                     "FROM income i JOIN incometype it ON i.incometypeid = it.incometypeid WHERE i.incomeid = ?";

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
        String sql = "UPDATE income SET incometypeid = ?, amount = ?, date = ? WHERE incomeid = ?";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, income.getIncomeTypeId());
            stmt.setDouble(2, income.getAmount());
            stmt.setDate(3, Date.valueOf(income.getDate()));
            stmt.setInt(4, income.getIncomeId());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete an income
    public boolean deleteIncome(int incomeId) {
        String sql = "DELETE FROM income WHERE incomeid = ?";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, incomeId);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
