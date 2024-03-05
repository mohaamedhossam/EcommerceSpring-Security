package com.springecommerce.error;

public class AlreadyReviewedException extends Exception {
    public AlreadyReviewedException() {
    }

    public AlreadyReviewedException(String message) {
        super(message);
    }

    public AlreadyReviewedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyReviewedException(Throwable cause) {
        super(cause);
    }

    protected AlreadyReviewedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
