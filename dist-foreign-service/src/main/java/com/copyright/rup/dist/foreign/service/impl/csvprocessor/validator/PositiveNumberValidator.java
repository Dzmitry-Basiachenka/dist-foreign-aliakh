package com.copyright.rup.dist.foreign.service.impl.csvprocessor.validator;

import org.apache.commons.lang3.StringUtils;

/**
 * The validator to check whether passed value contains only digits and the value is positive.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/27/17
 *
 * @author Ihar Suvorau
 */
public class PositiveNumberValidator implements IValidator<String> {

    private static final String POSITIVE_NUMBER_REGEX = "[1-9][0-9]*";

    @Override
    public boolean isValid(String value) {
        return StringUtils.isEmpty(value) || value.matches(POSITIVE_NUMBER_REGEX);
    }

    @Override
    public String getErrorMessage() {
        return "Field value should be positive number";
    }
}
