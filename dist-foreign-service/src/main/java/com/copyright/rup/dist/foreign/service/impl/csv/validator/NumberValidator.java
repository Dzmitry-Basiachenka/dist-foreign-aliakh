package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.IValidator;

import org.apache.commons.lang3.StringUtils;

/**
 * The validator to check whether passed value contains only digits.
 * Supports scientific notations for numbers (e.g. 1.1E+015).
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/13/21
 *
 * @author Anton Azarenka
 */
public class NumberValidator implements IValidator<String> {

    @Override
    public boolean isValid(String value) {
        return StringUtils.isEmpty(value) || value.matches("[0-9]\\d*(\\.\\d*[eE][+]\\d+)?");
    }

    @Override
    public String getErrorMessage() {
        return "Field value should contain only digits";
    }
}
