package com.marcelus.securinguserviceexercise.security.service.client.models;

public class AuthToken {
    private String jwt;

    public AuthToken(String jwt) {
        this.jwt = jwt;
    }

    public AuthToken() {}

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) { this.jwt = jwt; }
}
