package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import org.apache.commons.lang3.StringUtils;

/**
 * The validator to check if value is numeric and in range from 1000 to 9999.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/27/17
 *
 * @author Ihar Suvorau
 */
public class YearValidator implements IValidator<String> {

    private static final String YEAR_REGEX = "[1-9]\\d{3}";

    @Override
    public boolean isValid(String value) {
        return StringUtils.isEmpty(value) || value.matches(YEAR_REGEX);
    }

    @Override
    public String getErrorMessage() {
        return "Field value should be numeric in range from 1000 to 9999";
    }
}
