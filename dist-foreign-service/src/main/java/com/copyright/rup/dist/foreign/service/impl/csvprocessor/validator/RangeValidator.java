package com.copyright.rup.dist.foreign.service.impl.csvprocessor.validator;

import static com.google.common.base.Preconditions.checkArgument;

import org.apache.commons.lang3.StringUtils;

/**
 * The validator to check if value is within the specified range.
 * Throws {@link IllegalArgumentException} if both range values are nulls.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/27/17
 *
 * @author Ihar Suvorau
 */
public class RangeValidator implements IValidator<String> {

    private Integer minValue;
    private Integer maxValue;
    private String message;

    /**
     * Constructor.
     *
     * @param minValue min value
     * @param maxValue max value
     */
    public RangeValidator(Integer minValue, Integer maxValue) {
        checkArgument(null != maxValue || null != minValue);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public boolean isValid(String value) {
        Integer parsedValue = parseValue(value);
        if (null == parsedValue) {
            message = StringUtils.EMPTY;
            return true;
        } else if (null != minValue && null != maxValue) {
            message = String.format("Field value should be in range from %d to %d", minValue, maxValue);
            return minValue <= parsedValue && maxValue >= parsedValue;
        } else if (null != minValue) {
            message = String.format("Field value should be greater then %d", minValue);
            return minValue <= parsedValue;
        } else {
            message = String.format("Field value should be less then %d", maxValue);
            return maxValue >= parsedValue;
        }
    }

    @Override
    public String getErrorMessage() {
        return message;
    }

    private Integer parseValue(String value) {
        try {
            return Integer.valueOf(StringUtils.trim(value));
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
