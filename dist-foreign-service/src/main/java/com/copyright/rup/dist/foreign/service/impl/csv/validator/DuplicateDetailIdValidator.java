package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.csv.DistCsvProcessor;

/**
 * The validator to check whether usage with detail id is present in database.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 04/01/18
 *
 * @author Pavel Liakh
 */
public class DuplicateDetailIdValidator implements DistCsvProcessor.IValidator<Usage> {

    private final IUsageService usageService;

    /**
     * Constructor.
     *
     * @param usageService usage service
     */
    public DuplicateDetailIdValidator(IUsageService usageService) {
        this.usageService = usageService;
    }

    @Override
    public boolean isValid(Usage usage) {
        return !usageService.isDetailIdExists(usage.getDetailId());
    }

    @Override
    public String getErrorMessage() {
        return "Detail ID: Detail with such ID already exists";
    }
}
