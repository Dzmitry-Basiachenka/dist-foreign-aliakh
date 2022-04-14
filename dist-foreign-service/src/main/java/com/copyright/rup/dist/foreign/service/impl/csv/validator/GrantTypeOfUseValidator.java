package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;

import org.apache.commons.lang3.StringUtils;

/**
 * The validator to check if value is PRINT or DIGITAL.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/14/2022
 *
 * @author Ihar Suvorau
 */
public class GrantTypeOfUseValidator implements DistCsvProcessor.IValidator<String> {

    @Override
    public boolean isValid(String value) {
        return StringUtils.isEmpty(value) || "PRINT".equalsIgnoreCase(value) || "DIGITAL".equalsIgnoreCase(value);
    }

    @Override
    public String getErrorMessage() {
        return "Field value should be PRINT or DIGITAL";
    }
}
