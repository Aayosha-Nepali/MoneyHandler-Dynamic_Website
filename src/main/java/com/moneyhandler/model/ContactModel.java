package com.moneyhandler.model;

public class ContactModel {
    private int contactId;
    private String name;
    private String email;
    private String subject;
    private String message;
    private String status;

    // Constructors
    public ContactModel() {}

    public ContactModel(int contactId, String name, String email, String subject, String message, String status) {
        this.contactId = contactId;
        this.name = name;
        this.email = email;
        this.subject = subject;
        this.message = message;
        this.status = status;
    }

    // Getters and Setters
    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
