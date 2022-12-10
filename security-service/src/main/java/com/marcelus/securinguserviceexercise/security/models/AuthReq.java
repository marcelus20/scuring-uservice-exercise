package com.marcelus.securinguserviceexercise.security.models;

public class AuthReq {

    private String username;
    private String password;

    public AuthReq(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AuthReq() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
