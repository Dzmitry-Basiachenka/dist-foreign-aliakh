package com.copyright.rup.dist.foreign.ui.component.validator;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;

import com.vaadin.data.validator.AbstractStringValidator;

import org.apache.commons.lang3.StringUtils;

/**
 * Validator to check that given value is not negative number.
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
        super(ForeignUi.getMessage("field.error.number_format"));
    }

    @Override
    protected boolean isValidValue(String value) {
        return StringUtils.isNumeric(value);
    }
}
