package com.copyright.rup.dist.foreign.vui.common.validator;

import com.copyright.rup.dist.foreign.vui.main.ForeignUi;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.AbstractValidator;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * The validator to check that BigDecimal value is not null.
 * <p>
 * Copyright (C) 2024 copyright.com
 * <p>
 * Date: 01/22/2024
 *
 * @author Ihar Suvorau
 */
public class RequiredBigDecimalValidator extends AbstractValidator<BigDecimal> {

    private static final long serialVersionUID = -6340244211878446594L;

    /**
     * Constructor.
     */
    public RequiredBigDecimalValidator() {
        super(ForeignUi.getMessage("field.error.empty"));
    }

    @Override
    public ValidationResult apply(BigDecimal value, ValueContext context) {
        return toResult(value, Objects.nonNull(value));
    }
}
