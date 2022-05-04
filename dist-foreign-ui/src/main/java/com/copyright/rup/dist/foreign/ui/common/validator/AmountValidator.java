package com.copyright.rup.dist.foreign.ui.common.validator;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.AbstractValidator;

import org.apache.commons.lang3.StringUtils;

/**
 * The validator to check whether passed value is positive number or zero.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 10/27/2021
 *
 * @author Dzmitry Basiachenka
 */
public class AmountValidator extends AbstractValidator<String> {

    private static final String AMOUNT_REGEX = "(0|([1-9]\\d{0,9}))(\\.\\d{1,10})?";

    /**
     * Constructor.
     */
    public AmountValidator() {
        super(ForeignUi.getMessage("field.error.positive_number_or_zero_and_length", 10));
    }

    /**
     * Constructor.
     *
     * @param errorMessage the error message
     */
    public AmountValidator(String errorMessage) {
        super(errorMessage);
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
        return StringUtils.isEmpty(trimmedValue) || trimmedValue.matches(AMOUNT_REGEX);
    }
}
