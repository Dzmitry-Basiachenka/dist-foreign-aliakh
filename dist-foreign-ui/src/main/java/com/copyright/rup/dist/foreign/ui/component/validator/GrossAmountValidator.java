package com.copyright.rup.dist.foreign.ui.component.validator;

import com.vaadin.data.validator.AbstractStringValidator;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

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
     * Regular expression to validate that value is positive and contains 2 decimal places.
     */
    private static final String AMOUNT_REGEX = "(0|([1-9]\\d{0,9}))\\.\\d{2}";

    /**
     * Constructs a validator for amounts.
     */
    public GrossAmountValidator() {
        super("Field should be greater than 0 and contain 2 decimals");
    }

    @Override
    protected boolean isValidValue(String value) {
        String grossAmount = StringUtils.trimToEmpty(value);
        return grossAmount.matches(AMOUNT_REGEX) && -1 == BigDecimal.ZERO.compareTo(new BigDecimal(grossAmount));
    }
}
