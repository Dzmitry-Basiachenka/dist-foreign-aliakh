package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import org.apache.commons.lang3.StringUtils;

/**
 * Validator for ACLCI grade.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/19/2022
 *
 * @author Aliaksandr Liakh
 */
public class AclciGradeValidator implements DistCsvProcessor.IValidator<String> {

    private static final String GRADE_REGEX = "K|([1-9]|1[0-2])|HE";

    @Override
    public boolean isValid(String value) {
        return StringUtils.isNotEmpty(value) && value.matches(GRADE_REGEX);
    }

    @Override
    public String getErrorMessage() {
        return "Field value should be any of: K, 1-12, HE";
    }
}
