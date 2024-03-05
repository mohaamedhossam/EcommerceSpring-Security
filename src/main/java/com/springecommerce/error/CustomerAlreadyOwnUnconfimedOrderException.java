package com.springecommerce.error;

public class CustomerAlreadyOwnUnconfimedOrderException extends Exception {
    public CustomerAlreadyOwnUnconfimedOrderException() {
    }

    public CustomerAlreadyOwnUnconfimedOrderException(String message) {
        super(message);
    }

    public CustomerAlreadyOwnUnconfimedOrderException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomerAlreadyOwnUnconfimedOrderException(Throwable cause) {
        super(cause);
    }

    protected CustomerAlreadyOwnUnconfimedOrderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
