package com.newspring.delivery.exceptions;

public class RequestProcessingException extends RuntimeException{
    public RequestProcessingException() {
    }

    public RequestProcessingException(String message) {
        super(message);
    }

    public RequestProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestProcessingException(Throwable cause) {
        super(cause);
    }
}
