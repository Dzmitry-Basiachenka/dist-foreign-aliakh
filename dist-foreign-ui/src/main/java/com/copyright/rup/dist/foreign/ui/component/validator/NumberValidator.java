package com.copyright.rup.dist.foreign.ui.component.validator;

import com.vaadin.data.validator.AbstractStringValidator;

import org.apache.commons.lang3.StringUtils;

/**
 * Validator to check that given value only contains digits, and is non-null and not-negatives.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/20/2017
 *
 * @author Mikita Hladkikh
 */
public class NumberValidator extends AbstractStringValidator {

    /**
     * Constructor.
     */
    public NumberValidator() {
        super("Field value should contain numeric values only");
    }

    @Override
    protected boolean isValidValue(String value) {
        return StringUtils.isNumeric(StringUtils.trim(value));
    }
}
