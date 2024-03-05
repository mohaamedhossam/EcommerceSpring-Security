package com.springecommerce.error;

public class OrderProductNotFoundException extends Exception {
    public OrderProductNotFoundException() {
    }

    public OrderProductNotFoundException(String message) {
        super(message);
    }

    public OrderProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderProductNotFoundException(Throwable cause) {
        super(cause);
    }

    protected OrderProductNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
