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

    private static final String AMOUNT_REGEX = "(0|([1-9]\\d{0,9}))(\\.\\d+)?";

    /**
     * Constructor.
     */
    public AmountValidator() {
        super(ForeignUi.getMessage("field.error.positive_number_or_zero_and_length", 10));
    }

    @Override
    public ValidationResult apply(String value, ValueContext context) {
        return toResult(value, isValid(StringUtils.trimToEmpty(value)));
    }

    private boolean isValid(String value) {
        return StringUtils.isEmpty(value) || value.matches(AMOUNT_REGEX);
    }
}
