package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.foreign.service.impl.csv.DistCsvProcessor;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;

/**
 * The validator to check whether passed value format is 'MM/dd/yyyy' or 'M/d/yyyy'.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/27/17
 *
 * @author Ihar Suvorau
 */
public class DateFormatValidator implements DistCsvProcessor.IValidator<String> {

    private static final String DATE_REGEX = "\\d{1,2}/\\d{1,2}/\\d{4}";
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
        DateTimeFormatter.ofPattern("M/d/uuuu", Locale.US).withResolverStyle(ResolverStyle.STRICT);

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
            return value.matches(DATE_REGEX) && null != LocalDate.parse(value, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
