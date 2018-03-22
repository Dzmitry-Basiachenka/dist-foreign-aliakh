package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

    private static final String AMOUNT_REGEX = "(0|([1-9]\\d{0,9}))(\\.\\d+)?";

    @Override
    public boolean isValid(String value) {
        return StringUtils.isEmpty(value) || isValidReportedValue(value);
    }

    @Override
    public String getErrorMessage() {
        return "Field value should be greater than 0 after rounding";
    }

    private boolean isValidReportedValue(String value) {
        return value.matches(AMOUNT_REGEX)
            && 0 > BigDecimal.ZERO.compareTo(new BigDecimal(value).setScale(2, RoundingMode.HALF_UP));
    }
}
