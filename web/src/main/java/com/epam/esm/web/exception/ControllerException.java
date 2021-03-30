package com.epam.esm.web.exception;

/*Package-private*/ class ControllerException extends RuntimeException {
    /*Package-private*/ ControllerException(String message) {
        super(message);
    }

    /*Package-private*/ ControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    /*Package-private*/ ControllerException(Throwable cause) {
        super(cause);
    }

    /*Package-private*/ ControllerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
