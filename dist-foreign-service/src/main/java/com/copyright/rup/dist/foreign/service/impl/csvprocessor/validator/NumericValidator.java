package com.copyright.rup.dist.foreign.service.impl.csvprocessor.validator;

import org.apache.commons.lang3.StringUtils;

/**
 * The validator to check whether passed value contains only digits.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/27/17
 *
 * @author Ihar Suvorau
 */
public class NumericValidator implements IValidator<String> {

    @Override
    public boolean isValid(String value) {
        return StringUtils.isEmpty(value) || StringUtils.isNumeric(value.trim());
    }

    @Override
    public String getErrorMessage() {
        return "Field value should be numeric and positive";
    }
}
