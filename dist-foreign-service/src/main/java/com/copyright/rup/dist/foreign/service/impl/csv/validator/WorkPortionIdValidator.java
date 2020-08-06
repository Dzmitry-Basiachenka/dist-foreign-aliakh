package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.sal.ISalUsageService;

/**
 * Validates that provided Work portion Id is unique.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 08/05/2020
 *
 * @author Ihar Suvorau
 */
public class WorkPortionIdValidator implements DistCsvProcessor.IValidator<Usage> {

    private final ISalUsageService salUsageService;

    /**
     * Constructor.
     *
     * @param salUsageService instance of {@link ISalUsageService}
     */
    public WorkPortionIdValidator(ISalUsageService salUsageService) {
        this.salUsageService = salUsageService;
    }

    @Override
    public boolean isValid(Usage value) {
        return !salUsageService.workPortionIdExists(value.getSalUsage().getReportedWorkPortionId());
    }

    @Override
    public String getErrorMessage() {
        return "Usage with such Work Portion Id already exists in the system";
    }
}
