package com.springecommerce.entity;

import org.springframework.http.HttpStatus;

public class ErrorMessage {
    private HttpStatus status;
    private String message;

    public ErrorMessage(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public ErrorMessage() {
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString() {
        String var10000 = String.valueOf(this.status);
        return "ErrorMessage{status=" + var10000 + ", message='" + this.message + "'}";
    }
}
