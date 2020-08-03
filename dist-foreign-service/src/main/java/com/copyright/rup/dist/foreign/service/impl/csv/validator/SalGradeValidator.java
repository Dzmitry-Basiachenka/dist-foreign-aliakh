package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;

import org.apache.commons.lang3.StringUtils;

/**
 * Validator to verify that Grade is valid in range of K-12 including K,12.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/30/2020
 *
 * @author Ihar Suvorau
 */
public class SalGradeValidator implements DistCsvProcessor.IValidator<String> {

    private static final String GRADE_REGEX = "K|([1-9]|1[0-2])";

    @Override
    public boolean isValid(String value) {
        return StringUtils.isEmpty(value) || value.matches(GRADE_REGEX);
    }

    @Override
    public String getErrorMessage() {
        return "Field value should be in range K-12 including K,12";
    }
}
