package com.copyright.rup.dist.foreign.service.impl.csvprocessor.validator;

import com.copyright.rup.common.date.RupDateUtils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        return "Field value should have MM/dd/yyyy format";
    }

    private boolean isValidDateFormat(String value) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT, Locale.US);
            dateFormat.setLenient(false);
            return null != dateFormat.parse(value.trim());
        } catch (ParseException e) {
            return false;
        }
    }
}
