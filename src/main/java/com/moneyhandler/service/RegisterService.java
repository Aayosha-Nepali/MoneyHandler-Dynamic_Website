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

        String insertQuery = "INSERT INTO User (username, email, date_of_birth, contact_number) "
                + "VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = dbConn.prepareStatement(insertQuery)) {

            stmt.setString(1, userModel.getUsername());
            stmt.setString(2, userModel.getEmail());
            stmt.setDate(3, Date.valueOf(userModel.getDateOfBirth()));
            stmt.setString(4, userModel.getContactNumber());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error during user registration: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
