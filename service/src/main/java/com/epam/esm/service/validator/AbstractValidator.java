package com.epam.esm.service.validator;

import org.springframework.stereotype.Component;

@Component
public abstract class AbstractValidator<T> {
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    /*package-private*/ void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public abstract boolean validate(T t);
}
