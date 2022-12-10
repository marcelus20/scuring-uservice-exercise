package com.marcelus.securinguserviceexercise.security.models;

public class AuthResp extends AuthToken{

    public AuthResp(String jwt) {
        super(jwt);
    }
}
