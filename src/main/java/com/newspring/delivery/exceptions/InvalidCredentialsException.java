package com.newspring.delivery.exceptions;

public class InvalidCredentialsException extends Exception {
    public InvalidCredentialsException() {
        super("The password is not correct or the user was not found");
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }

    public InvalidCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCredentialsException(Throwable cause) {
        super(cause);
    }
}
