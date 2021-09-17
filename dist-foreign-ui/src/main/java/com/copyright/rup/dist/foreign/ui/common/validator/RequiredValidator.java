package com.copyright.rup.dist.foreign.ui.common.validator;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.AbstractValidator;

import org.apache.commons.lang3.StringUtils;

/**
 * The validator to check that value is not blank.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/17/2021
 *
 * @author Dzmitry Basiachenka
 */
public class RequiredValidator extends AbstractValidator<String> {

    /**
     * Constructor.
     */
    public RequiredValidator() {
        super(ForeignUi.getMessage("field.error.empty"));
    }

    @Override
    public ValidationResult apply(String value, ValueContext context) {
        return toResult(value, StringUtils.isNotBlank(value));
    }
}
