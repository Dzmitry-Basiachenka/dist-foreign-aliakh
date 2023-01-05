package com.copyright.rup.dist.foreign.ui.common.validator;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.AbstractValidator;
import org.apache.commons.lang3.StringUtils;

/**
 * The validator to check coverage years.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 01/05/2023
 *
 * @author Aliaksandr Liakh
 */
public class CoverageYearsValidator extends AbstractValidator<String> {

    private static final int YEARS_LENGTH = 9;

    /**
     * Constructor.
     *
     * @param errorMessage error message
     */
    public CoverageYearsValidator(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public ValidationResult apply(String value, ValueContext context) {
        if (StringUtils.isBlank(value)) {
            return toResult(value, false);
        }
        String years = value.trim();
        if (years.length() != YEARS_LENGTH) {
            return toResult(value, false);
        }
        char separator = years.charAt(4);
        String part1 = years.substring(0, 4);
        String part2 = years.substring(5);
        if (separator != '-' || !StringUtils.isNumeric(part1) || !StringUtils.isNumeric(part2)) {
            return toResult(value, false);
        }
        int year1 = Integer.parseInt(part1);
        int year2 = Integer.parseInt(part2);
        return toResult(value, isValidYear(year1) && isValidYear(year2) && year1 < year2);
    }

    private boolean isValidYear(int value) {
        return value >= PeriodValidator.MIN_YEAR && value <= PeriodValidator.MAX_YEAR;
    }
}
