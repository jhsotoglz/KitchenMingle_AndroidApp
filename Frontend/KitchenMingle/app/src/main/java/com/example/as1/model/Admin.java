package com.example.as1.model;

public class Admin {

    private Long id;
    private String username;
    private String email;
    // Omitted the password field because it should not be exposed through the API

    // Default constructor
    public Admin() {
    }

    // Parameterized constructor for all fields
    public Admin(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
        // Password here missing because is not meant to be exposed
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    // The getPassword and setPassword methods are intentionally omitted to avoid exposing passwords

    // toString method for debugging
    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

