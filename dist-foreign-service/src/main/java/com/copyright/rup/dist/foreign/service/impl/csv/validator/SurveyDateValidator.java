package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.foreign.domain.UdmUsage;

import java.util.Objects;

/**
 * The validator to check that Survey End Date greater than or equal to Survey Start Date.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/03/21
 *
 * @author Uladzislau Shalamitski
 */
public class SurveyDateValidator implements DistCsvProcessor.IValidator<UdmUsage> {

    @Override
    public boolean isValid(UdmUsage udmUsage) {
        return 0 <= Objects.requireNonNull(udmUsage).getSurveyEndDate().compareTo(udmUsage.getSurveyStartDate());
    }

    @Override
    public String getErrorMessage() {
        return "Survey End Date should be greater than or equal to Survey Start Date";
    }
}
