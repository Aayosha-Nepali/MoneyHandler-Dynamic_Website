package com.moneyhandler.dao;

import com.moneyhandler.config.DbConfig;
import com.moneyhandler.model.UserModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * UserDAO handles database operations related to users.
 */
public class UserDAO {

    /**
     * Fetches a user by email (used in login).
     */
    public UserModel getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractUser(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Fetches a user by ID (used for profile).
     */
    public UserModel getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE userid = ?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractUser(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates user profile.
     */
    public boolean updateUser(UserModel user) {
        String sql = "UPDATE users SET username = ?, email = ?, dob = ?, contactnumber = ? WHERE userid = ?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setDate(3, java.sql.Date.valueOf(user.getDateOfBirth()));
            stmt.setString(4, user.getContactNumber());
            stmt.setInt(5, user.getUserId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Helper method to extract a UserModel from ResultSet.
     */
    private UserModel extractUser(ResultSet rs) throws SQLException {
        UserModel user = new UserModel();
        user.setUserId(rs.getInt("userid"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setDateOfBirth(rs.getDate("dob").toLocalDate());
        user.setContactNumber(rs.getString("contactnumber"));
        user.setPassword(rs.getString("password"));
        user.setImagePath(rs.getString("image_path"));
        return user;
    }
}
