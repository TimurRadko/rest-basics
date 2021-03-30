package com.epam.esm.web.exception.handler;

public class GiftCertificateIncorrectData {
    private String errorMessage;
    private Integer errorCode;

    /*package-private*/ GiftCertificateIncorrectData() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    /*package-private*/ void setMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    /*package-private*/ void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
