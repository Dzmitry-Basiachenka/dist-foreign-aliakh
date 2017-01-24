package com.copyright.rup.dist.foreign.ui.component.validator;

import com.vaadin.data.validator.BigDecimalRangeValidator;

import java.math.BigDecimal;

/**
 * Validator for validating that given value is in range from 0 to 9999999999,99.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/19/2017
 *
 * @author Mikita Hladkikh
 */
public class AmountValidator extends BigDecimalRangeValidator {

    /**
     * Constructs validator.
     */
    public AmountValidator() {
        super("Value field range should be from 0 to 9999999999.99",
            BigDecimal.ZERO, BigDecimal.valueOf(Math.pow(10, 10)).subtract(new BigDecimal("0.01")));
    }
}
