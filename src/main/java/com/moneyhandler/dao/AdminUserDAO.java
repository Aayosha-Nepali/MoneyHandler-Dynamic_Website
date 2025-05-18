package com.moneyhandler.dao;

import com.moneyhandler.config.DbConfig;
import com.moneyhandler.model.UserModel;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class for admin-side user management.
 */
public class AdminUserDAO {

    // Fetch all users
    public List<UserModel> getAllUsers() {
        List<UserModel> users = new ArrayList<>();
        String sql = "SELECT * FROM User";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                UserModel user = new UserModel();
                user.setUserId(rs.getInt("UserID"));
                user.setUsername(rs.getString("Username"));
                user.setEmail(rs.getString("Email"));
                user.setDateOfBirth(rs.getDate("DOB").toLocalDate());
                user.setContactNumber(rs.getString("ContactNumber"));
                user.setPassword(rs.getString("Password"));
                user.setImagePath(rs.getString("image_path"));
                users.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    // Add new user manually
    public boolean addUser(UserModel user) {
        String sql = "INSERT INTO User (Username, Email, DOB, ContactNumber, Password, image_path) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setDate(3, Date.valueOf(user.getDateOfBirth()));
            stmt.setString(4, user.getContactNumber());
            stmt.setString(5, user.getPassword());
            stmt.setString(6, user.getImagePath());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // (Optional) Delete user by ID
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM User WHERE UserID = ?";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
