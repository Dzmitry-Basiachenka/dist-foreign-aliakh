package com.copyright.rup.dist.foreign.ui.common.validator;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.AbstractValidator;

import org.apache.commons.lang3.StringUtils;

/**
 * The validator to check whether passed value is number.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 02/07/2022
 *
 * @author Anton Azarenka
 */
public class NumericValidator extends AbstractValidator<String> {

    private static final long serialVersionUID = 8862386283553929466L;

    /**
     * Default Constructor.
     */
    public NumericValidator() {
        this(ForeignUi.getMessage("field.error.not_numeric"));
    }

    /**
     * Constructor.
     *
     * @param errorMessage the message to be included in a failed result, not null
     */
    public NumericValidator(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public ValidationResult apply(String value, ValueContext context) {
        return toResult(value, isValid(value));
    }

    /**
     * Performs validation of passed value.
     *
     * @param value the value to validate
     * @return the result of validation: {@code true} value passed validation otherwise {@code false}
     */
    public boolean isValid(String value) {
        return StringUtils.isEmpty(value) || StringUtils.isNumeric(value.trim());
    }
}
