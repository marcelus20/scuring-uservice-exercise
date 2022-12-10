package com.marcelus.securinguserviceexercise.mainapi.models;

import java.util.Date;

public class Health {

    private String message;
    private Date date;


    public Health() {
        message = "This api is healthy!";
        date = new Date();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
