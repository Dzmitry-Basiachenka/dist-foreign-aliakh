package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.sal.ISalUsageService;

import java.util.Objects;

/**
 * Validates that grade is provided by either loaded usage or existing item bank detail.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/23/2020
 *
 * @author Stanislau Rudak
 */
public class UsageDataGradeValidator implements DistCsvProcessor.IValidator<Usage> {

    private final ISalUsageService salUsageService;

    /**
     * Constructor.
     *
     * @param salUsageService instance of {@link ISalUsageService}
     */
    public UsageDataGradeValidator(ISalUsageService salUsageService) {
        this.salUsageService = salUsageService;
    }

    @Override
    public boolean isValid(Usage value) {
        return Objects.nonNull(value.getSalUsage().getGrade())
            || Objects.nonNull(
                salUsageService.getItemBankDetailGradeByWorkPortionId(value.getSalUsage().getReportedWorkPortionId()));
    }

    @Override
    public String getErrorMessage() {
        return "Grade should be specified in either Item Bank or Usage Data";
    }
}
