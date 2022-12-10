package com.marcelus.securinguserviceexercise.security.service.client.models;


public class ValidTokenAcknowledgement {
    private Boolean valid;

    private AuthToken authToken;

    public ValidTokenAcknowledgement(Boolean valid, AuthToken authToken) {
        this.valid = valid;
        this.authToken = authToken;
    }

    public ValidTokenAcknowledgement(AuthToken authToken) {
        this.authToken = authToken;
        this.valid = true;
    }

    public ValidTokenAcknowledgement() {
    }

    public Boolean getValid() {
        return valid;
    }

    public AuthToken getAuthToken(){
        return authToken;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
