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

    private static final String AMOUNT_REGEX = "(0|([1-9]\\d{0,9}))(\\.\\d+)?";

    /**
     * Constructor.
     */
    public AmountZeroValidator() {
        super(ForeignUi.getMessage("field.error.positive_number_and_length", 10));
    }

    @Override
    public ValidationResult apply(String value, ValueContext context) {
        return toResult(value, isValid(StringUtils.trimToEmpty(value)));
    }

    private boolean isValid(String value) {
        return StringUtils.isEmpty(value) || value.matches(AMOUNT_REGEX)
            && 0 > BigDecimal.ZERO.compareTo(new BigDecimal(value).setScale(2, RoundingMode.HALF_UP));
    }
}
