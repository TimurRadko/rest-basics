package com.epam.esm.web.exception;

public class InvalidRequestBodyException extends ControllerException {
    public InvalidRequestBodyException(String message) {
        super(message);
    }

    public InvalidRequestBodyException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRequestBodyException(Throwable cause) {
        super(cause);
    }

    public InvalidRequestBodyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
