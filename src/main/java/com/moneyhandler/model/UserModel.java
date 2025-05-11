package com.moneyhandler.model;

import java.time.LocalDate;
import java.time.Period;

/**
 * Represents a user in the MoneyHandler system.
 */
public class UserModel {

    private int userId;
    private String username;
    private String email;
    private LocalDate dateOfBirth;
    private String contactNumber;
    private String password;
    private String imagePath;
    
    // Constructors
    public UserModel() {}

    public UserModel(String username, String email, LocalDate dateOfBirth, String contactNumber, String password, String imagePath) {
        this.username = username;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.contactNumber = contactNumber;
        this.password = password;
        this.imagePath = imagePath;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    // Auto-calculate age from date of birth
    public int getAge() {
        return (dateOfBirth != null) ? Period.between(dateOfBirth, LocalDate.now()).getYears() : 0;
    }
}
