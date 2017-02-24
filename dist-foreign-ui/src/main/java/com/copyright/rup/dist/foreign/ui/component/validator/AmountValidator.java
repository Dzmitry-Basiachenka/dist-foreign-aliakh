package com.copyright.rup.dist.foreign.ui.component.validator;

import com.vaadin.data.validator.AbstractStringValidator;

import org.apache.commons.lang3.StringUtils;

/**
 * Validator for amounts. Checks that amount is greater than 0 and contain up to 2 decimals.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/24/17
 *
 * @author Darya Baraukova
 */
public class AmountValidator extends AbstractStringValidator {

    /**
     * Regular expression to validate that value is greater than zero allowing 2 decimal places.
     */
    private static final String AMOUNT_REGEX = "^((0\\.((0[1-9])|([1-9][0-9]?)))|([1-9][0-9]*)\\.?[0-9]{1,2})$";

    /**
     * Constructs a validator for amounts.
     */
    public AmountValidator() {
        super("Field should be greater than 0 and contain up to 2 decimals");
    }

    @Override
    protected boolean isValidValue(String value) {
        return StringUtils.trimToEmpty(value).matches(AMOUNT_REGEX);
    }
}
