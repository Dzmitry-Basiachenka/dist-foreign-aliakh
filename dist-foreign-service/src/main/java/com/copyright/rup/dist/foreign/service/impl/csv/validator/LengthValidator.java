package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import static com.google.common.base.Preconditions.checkArgument;

import com.copyright.rup.dist.foreign.service.impl.csv.DistCsvProcessor;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * The validator to check whether passed value is not longer than expected size.
 * If string is a number in scientific format system will parse it and verify size of parsed value.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/27/17
 *
 * @author Ihar Suvorau
 */
public class LengthValidator implements DistCsvProcessor.IValidator<String> {

    private final int maxLength;

    /**
     * Constructor.
     *
     * @param maxLength max length of the value
     */
    public LengthValidator(int maxLength) {
        checkArgument(maxLength > 0);
        this.maxLength = maxLength;
    }

    @Override
    public boolean isValid(String value) {
        return StringUtils.isEmpty(value) || isValidValue(value);
    }

    @Override
    public String getErrorMessage() {
        return String.format("Field value should not exceed %d characters", maxLength);
    }

    private boolean isValidValue(String value) {
        return isPositiveNumber(value) ? parseScientific(value).length() <= maxLength : value.length() <= maxLength;
    }

    private boolean isPositiveNumber(String value) {
        return null != value && value.matches("[1-9]\\d*(\\.\\d*[eE][+]\\d+)?");
    }

    private String parseScientific(String value) {
        return null != value ? new BigDecimal(value).toPlainString() : null;
    }
}
