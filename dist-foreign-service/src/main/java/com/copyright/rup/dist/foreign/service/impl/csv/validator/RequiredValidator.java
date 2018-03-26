package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.foreign.service.impl.csv.DistCsvProcessor;

import org.apache.commons.lang3.StringUtils;

/**
 * The validator to check whether passed value is not {@code null} and not empty.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/20/17
 *
 * @author Aliaksei Pchelnikau
 */
public class RequiredValidator implements DistCsvProcessor.IValidator<String> {

    @Override
    public boolean isValid(String value) {
        return StringUtils.isNotBlank(value);
    }

    @Override
    public String getErrorMessage() {
        return "Field is required and cannot be null or empty";
    }
}
