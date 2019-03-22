package com.se.exception;

public class DataServiceOperationException extends RuntimeException {
    public DataServiceOperationException(String message) {
        super(message);
    }

    public DataServiceOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
