package com.moneyhandler.dao;

import com.moneyhandler.config.DbConfig;
import com.moneyhandler.model.ContactModel;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ContactDAO {

    public boolean saveContact(ContactModel contact) {
        String sql = "INSERT INTO ContactUs (Name, Email, Subject, Message) VALUES (?, ?, ?, ?)";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, contact.getName());
            stmt.setString(2, contact.getEmail());
            stmt.setString(3, contact.getSubject());
            stmt.setString(4, contact.getMessage());

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
