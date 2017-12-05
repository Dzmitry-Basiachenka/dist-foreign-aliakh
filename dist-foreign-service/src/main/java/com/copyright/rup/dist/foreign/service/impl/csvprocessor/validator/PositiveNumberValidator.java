package com.copyright.rup.dist.foreign.service.impl.csvprocessor.validator;

import com.copyright.rup.dist.foreign.service.impl.csvprocessor.CommonCsvProcessor;

import org.apache.commons.lang3.StringUtils;

/**
 * The validator to check whether passed value contains only digits and the value is positive.
 * Supports scientific notations for numbers (e.g. 1.1E+015).
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/27/17
 *
 * @author Ihar Suvorau
 */
public class PositiveNumberValidator implements IValidator<String> {

    @Override
    public boolean isValid(String value) {
        return StringUtils.isEmpty(value) || CommonCsvProcessor.isPositiveNumber(value);
    }

    @Override
    public String getErrorMessage() {
        return "Field value should be positive number";
    }
}
