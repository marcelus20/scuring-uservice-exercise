package com.marcelus.securinguserviceexercise.security.models;

public class AuthToken {
    private final String jwt;

    public AuthToken(String jwt) {
        this.jwt = jwt;
    }


    public String getJwt() {
        return jwt;
    }
}
