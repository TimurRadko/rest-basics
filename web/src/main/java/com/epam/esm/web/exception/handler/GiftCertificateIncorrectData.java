package com.epam.esm.web.exception.handler;

public class GiftCertificateIncorrectData {
    private String errorMessage;
    private Integer errorCode;

    GiftCertificateIncorrectData() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    void setMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
