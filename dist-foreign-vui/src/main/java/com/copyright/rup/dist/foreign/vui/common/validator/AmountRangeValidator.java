package com.copyright.rup.dist.foreign.vui.common.validator;

import com.copyright.rup.dist.foreign.vui.main.ForeignUi;

import com.vaadin.flow.data.validator.BigDecimalRangeValidator;

import java.math.BigDecimal;

/**
 * The validator to check whether passed BigDecimal value within expected range.
 * <p>
 * Copyright (C) 2024 copyright.com
 * <p>
 * Date: 01/22/2024
 *
 * @author Ihar Suvorau
 */
public class AmountRangeValidator extends BigDecimalRangeValidator {

    private static final BigDecimal MIN_AMOUNT = new BigDecimal("0.01");
    private static final BigDecimal MAX_AMOUNT = new BigDecimal("9999999999.99");

    /**
     * Creates a validator for checking that an BigDecimal is within a given range.
     *
     * @param minimumAmount allowed minimum amount
     */
    public AmountRangeValidator(BigDecimal minimumAmount) {
        super(ForeignUi.getMessage("field.error.positive_number_and_length", 10), minimumAmount, MAX_AMOUNT);
    }

    /**
     * @return instance of AmountRangeValidator with zero minimum amount allowed
     */
    public static AmountRangeValidator zeroAmountValidator() {
        return new AmountRangeValidator(BigDecimal.ZERO);
    }

    /**
     * @return instance of AmountRangeValidator with 1 cent minimum amount
     */
    public static AmountRangeValidator amountValidator() {
        return new AmountRangeValidator(MIN_AMOUNT);
    }
}
