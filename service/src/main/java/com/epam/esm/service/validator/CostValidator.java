package com.epam.esm.service.validator;

import java.math.BigDecimal;

public class CostValidator extends AbstractValidator<BigDecimal> {

    @Override
    public boolean isValid(BigDecimal bigDecimal) {
        return false;
    }
}
