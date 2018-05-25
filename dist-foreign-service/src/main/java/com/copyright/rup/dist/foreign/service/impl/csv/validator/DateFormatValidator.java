package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.common.util.CommonDateUtils;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

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

    @Override
    public boolean isValid(String value) {
        return StringUtils.isEmpty(value) || isValidDateFormat(value);
    }

    @Override
    public String getErrorMessage() {
        return "Field value should have MM/dd/yyyy or M/d/yyyy format";
    }

    private boolean isValidDateFormat(String value) {
        return value.matches(DATE_REGEX) && Objects.nonNull(CommonDateUtils.parseLocalDate(value, "M/d/uuuu"));
    }
}
