package com.moneyhandler.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.moneyhandler.config.DbConfig;
import com.moneyhandler.model.UserModel;

/**
 * RegisterService handles user registration for MoneyHandler.
 */
public class RegisterService {

    private Connection dbConn;

    /**
     * Constructor initializes the database connection.
     */
    public RegisterService() {
        try {
            this.dbConn = DbConfig.getDbConnection();
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("Database connection error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Registers a new user in the database.
     *
     * @param userModel the user details to register
     * @return true if registration succeeded, false if failed, null on error
     */
    public Boolean registerUser(UserModel userModel) {
        if (dbConn == null) {
            System.err.println("Database connection is not available.");
            return null;
        }

        String insertQuery = "INSERT INTO User (Username, Email, Password, Date Of Birth (DOB), Contact Number, image_path) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = dbConn.prepareStatement(insertQuery)) {

            stmt.setString(1, userModel.getUsername());
            stmt.setString(2, userModel.getEmail());
            stmt.setString(3, userModel.getPassword());
            stmt.setDate(4, Date.valueOf(userModel.getDateOfBirth()));
            stmt.setString(5, userModel.getContactNumber());
            stmt.setString(6, userModel.getImagePath());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error during user registration: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
