package com.grasset.exception;

public class InvalidActionException extends RuntimeException {

    public InvalidActionException(String message) {
        super(message);
    }

    public InvalidActionException(Throwable cause) {
        super(cause);
    }
}
