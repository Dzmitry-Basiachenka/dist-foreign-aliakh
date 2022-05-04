package com.copyright.rup.dist.foreign.ui.common.validator;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.AbstractValidator;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * The validator to check whether passed value is positive number or zero with 2 decimals.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 10/27/2021
 *
 * @author Dzmitry Basiachenka
 */
public class AmountZeroValidator extends AbstractValidator<String> {

    private static final String AMOUNT_REGEX = "(0|([1-9]\\d{0,9}))(\\.\\d{1,10})?";

    /**
     * Constructor.
     */
    public AmountZeroValidator() {
        super(ForeignUi.getMessage("field.error.positive_number_and_length", 10));
    }

    @Override
    public ValidationResult apply(String value, ValueContext context) {
        return toResult(value, isValid(value));
    }

    /**
     * Performs validation of passed value.
     *
     * @param value the value to validate
     * @return the result of validation: {@code true} value passed validation otherwise {@code false}
     */
    public boolean isValid(String value) {
        String trimmedValue = StringUtils.trimToEmpty(value);
        return StringUtils.isEmpty(trimmedValue) || trimmedValue.matches(AMOUNT_REGEX)
            && 0 > BigDecimal.ZERO.compareTo(new BigDecimal(trimmedValue).setScale(2, RoundingMode.HALF_UP));
    }
}
