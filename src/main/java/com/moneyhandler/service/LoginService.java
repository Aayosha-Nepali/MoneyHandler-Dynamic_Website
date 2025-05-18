package com.moneyhandler.service;

import com.moneyhandler.config.DbConfig;
import com.moneyhandler.model.UserModel;

import java.sql.*;

public class LoginService {

    private Connection dbConn;

    public LoginService() {
        try {
            this.dbConn = DbConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public UserModel findUserByEmail(String email) {
    	if (dbConn == null) {
            System.err.println("Database connection is not available.");
            return null;
        }
        String sql = "SELECT * FROM User WHERE Email = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                UserModel user = new UserModel();
                user.setUserId(rs.getInt("UserID"));
                user.setUsername(rs.getString("Username"));
                user.setEmail(rs.getString("Email"));
                user.setDateOfBirth(rs.getDate("DOB").toLocalDate());
                user.setContactNumber(rs.getString("ContactNumber"));
                user.setPassword(rs.getString("Password"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
