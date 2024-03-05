package com.springecommerce.error;

public class EmpytCartException extends Exception {
    public EmpytCartException() {
    }

    public EmpytCartException(String message) {
        super(message);
    }

    public EmpytCartException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmpytCartException(Throwable cause) {
        super(cause);
    }

    protected EmpytCartException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
