package com.grasset.exception;

public class DBException extends RuntimeException {

    public DBException(String message) {
        super(message);
    }

    public DBException(Throwable cause) {
        super(cause);
    }
}
