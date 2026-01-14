package com.skillmirror.backend.entity;

public class LoginRequest {

    private String email;
    private String password;

    // REQUIRED no-args constructor
    public LoginRequest() {
    }

    // getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


