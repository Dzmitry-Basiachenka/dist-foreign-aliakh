package com.copyright.rup.dist.foreign.ui.component.validator;

import com.vaadin.data.validator.AbstractStringValidator;

import org.apache.commons.lang3.StringUtils;

/**
 * Validator for gross amounts. Checks that gross amount is greater than 0 and contain 2 decimals.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/24/17
 *
 * @author Darya Baraukova
 * @author Mikalai Bezmen
 */
public class GrossAmountValidator extends AbstractStringValidator {

    /**
     * Regular expression to validate that value is greater than zero and contain 2 decimal places.
     */
    private static final String AMOUNT_REGEX = "^((0\\.((0[1-9])|([1-9][0-9]?)))|([1-9][0-9]*)\\.[0-9]{2})$";

    /**
     * Constructs a validator for amounts.
     */
    public GrossAmountValidator() {
        super("Field should be greater than 0 and contain 2 decimals");
    }

    @Override
    protected boolean isValidValue(String value) {
        return StringUtils.trimToEmpty(value).matches(AMOUNT_REGEX);
    }
}
