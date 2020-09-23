package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.sal.ISalUsageService;

/**
 * Validates that provided Work portion Id exists in the system.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/22/2020
 *
 * @author Stanislau Rudak
 */
public class UsageDataWorkPortionIdValidator implements DistCsvProcessor.IValidator<Usage> {

    private final ISalUsageService salUsageService;
    private final String batchId;

    /**
     * Constructor.
     *
     * @param salUsageService instance of {@link ISalUsageService}
     * @param batchId         batch id
     */
    public UsageDataWorkPortionIdValidator(ISalUsageService salUsageService, String batchId) {
        this.salUsageService = salUsageService;
        this.batchId = batchId;
    }

    @Override
    public boolean isValid(Usage value) {
        return salUsageService.workPortionIdExists(value.getSalUsage().getReportedWorkPortionId(), batchId);
    }

    @Override
    public String getErrorMessage() {
        return "Work Portion Id is not present in the Item Bank";
    }
}
