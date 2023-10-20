package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;

import org.apache.commons.lang3.StringUtils;

/**
 * The validator to check whether passed value contains only integer digits including zero.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 10/19/2023
 *
 * @author Anton Azarenka
 */
public class IntegerValidator implements DistCsvProcessor.IValidator<String> {

    @Override
    public boolean isValid(String value) {
        return StringUtils.isEmpty(value) || StringUtils.isNumeric(value.trim());
    }

    @Override
    public String getErrorMessage() {
        return "Field value should contain only integer numbers";
    }
}
