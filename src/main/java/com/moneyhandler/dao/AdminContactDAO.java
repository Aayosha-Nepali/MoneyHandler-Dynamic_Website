package com.moneyhandler.dao;

import com.moneyhandler.config.DbConfig;
import com.moneyhandler.model.ContactModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for managing contact messages submitted by users.
 */
public class AdminContactDAO {

    // Fetch all contact messages
    public List<ContactModel> getAllContacts() {
        List<ContactModel> contacts = new ArrayList<>();
        String sql = "SELECT * FROM ContactUs ORDER BY ContactID DESC";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ContactModel contact = new ContactModel();
                contact.setContactId(rs.getInt("ContactID"));
                contact.setName(rs.getString("Name"));
                contact.setEmail(rs.getString("Email"));
                contact.setSubject(rs.getString("Subject"));
                contact.setStatus(rs.getString("Status"));
                contacts.add(contact);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contacts;
    }

    // Update status (Solved, Pending, etc.)
    public boolean updateStatus(int contactId, String newStatus) {
        String sql = "UPDATE ContactUs SET Status = ? WHERE ContactID = ?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newStatus);
            stmt.setInt(2, contactId);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a contact (only allowed after issue is Solved)
    public boolean deleteContact(int contactId) {
        String checkSql = "SELECT Status FROM ContactUs WHERE ContactID = ?";
        String deleteSql = "DELETE FROM ContactUs WHERE ContactID = ?";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setInt(1, contactId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                String status = rs.getString("Status");
                if ("Solved".equalsIgnoreCase(status)) {
                    try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                        deleteStmt.setInt(1, contactId);
                        return deleteStmt.executeUpdate() > 0;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
 // Alias for compatibility with export code
    public List<ContactModel> getAllMessages() {
        return getAllContacts();
    }

}
