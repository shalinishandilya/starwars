package com.shalini.starwars.exception;

import org.springframework.http.HttpStatus;

public class StarWarsError {
    private int status;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status.value();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
