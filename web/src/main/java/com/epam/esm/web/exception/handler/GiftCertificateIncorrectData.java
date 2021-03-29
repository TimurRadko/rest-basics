package com.epam.esm.web.exception.handler;

public class GiftCertificateIncorrectData {
    private String errorMessage;

    /*package-private*/ GiftCertificateIncorrectData() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    /*package-private*/ void setMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
