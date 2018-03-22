package com.copyright.rup.dist.foreign.ui.component.validator;

import com.copyright.rup.dist.foreign.service.impl.csv.validator.ReportedValueValidator;

import com.vaadin.data.validator.AbstractStringValidator;

import org.apache.commons.lang3.StringUtils;

/**
 * Validator for gross amounts. Checks that gross amount is greater than 0.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 02/24/17
 *
 * @author Darya Baraukova
 * @author Mikalai Bezmen
 */
public class GrossAmountValidator extends AbstractStringValidator {

    /**
     * Constructs a validator for amounts.
     */
    public GrossAmountValidator() {
        super("Field value should be positive number and not exceed 10 digits");
    }

    @Override
    protected boolean isValidValue(String value) {
        return new ReportedValueValidator().isValid(StringUtils.trimToEmpty(value));
    }
}
