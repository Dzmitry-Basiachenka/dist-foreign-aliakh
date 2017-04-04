package com.copyright.rup.dist.foreign.service.impl.csvprocessor.validator;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * The validator to check whether passed value is positive number with 2 decimals.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/27/17
 *
 * @author Ihar Suvorau
 */
public class ReportedValueValidator implements IValidator<String> {

    private static final String AMOUNT_REGEX = "(0|([1-9]\\d{0,9}))\\.\\d{2}";

    @Override
    public boolean isValid(String value) {
        return StringUtils.isEmpty(value) || isValidReportedValue(value);
    }

    @Override
    public String getErrorMessage() {
        return "Field value should be greater than 0 and contain 2 decimals";
    }

    private boolean isValidReportedValue(String value) {
        return value.matches(AMOUNT_REGEX) && -1 == BigDecimal.ZERO.compareTo(new BigDecimal(value));
    }
}
