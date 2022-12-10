package com.marcelus.securinguserviceexercise.security.models;

public class AuthResp {

    private final String jwt;

    public AuthResp(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
