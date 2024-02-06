package com.copyright.rup.dist.foreign.vui.common.validator;

import com.copyright.rup.dist.foreign.vui.main.ForeignUi;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.AbstractValidator;

import java.util.Objects;

/**
 * The validator to check that Long value is not null.
 * <p>
 * Copyright (C) 2024 copyright.com
 * <p>
 * Date: 01/22/2024
 *
 * @author Ihar Suvorau
 */
public class RequiredNumberValidator extends AbstractValidator<Number> {

    private static final long serialVersionUID = 7818434813526555831L;

    /**
     * Constructor.
     */
    public RequiredNumberValidator() {
        super(ForeignUi.getMessage("field.error.empty"));
    }

    @Override
    public ValidationResult apply(Number value, ValueContext context) {
        return toResult(value, Objects.nonNull(value));
    }
}
