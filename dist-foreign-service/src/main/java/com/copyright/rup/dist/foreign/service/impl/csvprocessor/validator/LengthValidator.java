package com.copyright.rup.dist.foreign.service.impl.csvprocessor.validator;

import static com.google.common.base.Preconditions.checkArgument;

import org.apache.commons.lang3.StringUtils;

/**
 * The validator to check whether passed value is not longer than expected size.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/27/17
 *
 * @author Ihar Suvorau
 */
public class LengthValidator implements IValidator<String> {

    private final int maxLength;

    /**
     * Constructor.
     *
     * @param maxLength max length of the value
     */
    public LengthValidator(int maxLength) {
        checkArgument(maxLength > 0);
        this.maxLength = maxLength;
    }

    @Override
    public boolean isValid(String value) {
        return StringUtils.isEmpty(value) || value.length() <= maxLength;
    }

    @Override
    public String getErrorMessage() {
        return String.format("Field value should not exceed %d characters", maxLength);
    }
}
