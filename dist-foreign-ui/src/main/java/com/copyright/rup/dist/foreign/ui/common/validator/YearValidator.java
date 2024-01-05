package com.copyright.rup.dist.foreign.ui.common.validator;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.AbstractValidator;
import org.apache.commons.lang3.StringUtils;

/**
 * Class to validate years.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 10/18/2021
 *
 * @author Aliaksandr Liakh
 */
public class YearValidator extends AbstractValidator<String> {

    private static final long serialVersionUID = -3375196726841313175L;
    private static final int MIN_YEAR = 1950;
    private static final int MAX_YEAR = 2099;

    /**
     * Constructor.
     */
    public YearValidator() {
        super(ForeignUi.getMessage("field.error.number_not_in_range", MIN_YEAR, MAX_YEAR));
    }

    @Override
    public ValidationResult apply(String value, ValueContext context) {
        return toResult(value, StringUtils.isBlank(value) || isValid(StringUtils.trimToEmpty(value)));
    }

    private boolean isValid(String value) {
        return Integer.parseInt(value) >= MIN_YEAR && Integer.parseInt(value) <= MAX_YEAR;
    }
}
