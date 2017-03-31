package com.copyright.rup.dist.foreign.service.impl.csvprocessor.validator;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * The validator to check whether passed value format is 'MM/dd/yyyy'.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/27/17
 *
 * @author Ihar Suvorau
 */
public class DateFormatValidator implements IValidator<String> {

    @Override
    public boolean isValid(String value) {
        return StringUtils.isEmpty(value) || isValidDateFormat(value);
    }

    @Override
    public String getErrorMessage() {
        return "Field value should have MM/dd/yyyy or M/d/yyyy format";
    }

    private boolean isValidDateFormat(String value) {
        try {
            return null != LocalDate.parse(value, DateTimeFormatter.ofPattern("M/d/yyyy", Locale.US));
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
