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
        String sql = "SELECT * FROM User WHERE Email = ?";
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
        String sql = "SELECT * FROM User WHERE UserID = ?";
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
        String sql = "UPDATE User SET Username = ?, Email = ?, DOB = ?, ContactNumber = ? WHERE UserID = ?";
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
        user.setUserId(rs.getInt("UserID"));
        user.setUsername(rs.getString("Username"));
        user.setEmail(rs.getString("Email"));
        user.setDateOfBirth(rs.getDate("DOB").toLocalDate());
        user.setContactNumber(rs.getString("ContactNumber"));
        user.setPassword(rs.getString("Password"));
        user.setImagePath(rs.getString("image_path"));
        return user;
    }
    public boolean updateUserProfile(UserModel user) {
        String sql = "UPDATE User SET ContactNumber = ?, Password = ?, image_path = ? WHERE UserID = ?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getContactNumber());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getImagePath());
            stmt.setInt(4, user.getUserId());

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
