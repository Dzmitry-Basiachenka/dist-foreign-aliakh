package com.copyright.rup.dist.foreign.vui.common.validator;

import com.copyright.rup.dist.foreign.vui.main.ForeignUi;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.AbstractValidator;

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

    private static final long serialVersionUID = 2267019253181583757L;

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
