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
        String sql = "SELECT * FROM User WHERE email = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                UserModel user = new UserModel();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
                user.setContactNumber(rs.getString("contact_number"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
