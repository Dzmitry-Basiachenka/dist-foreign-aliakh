package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.csv.DistCsvProcessor;

import java.util.Objects;

/**
 * The validator for status of {@link ResearchedUsage}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 03/27/2018
 *
 * @author Aliaksandr Liakh
 */
public class ResearchedUsageStatusValidator implements DistCsvProcessor.IValidator<ResearchedUsage> {

    private final IUsageService usageService;

    /**
     * Constructor.
     *
     * @param usageService instance of {@link IUsageService}
     */
    public ResearchedUsageStatusValidator(IUsageService usageService) {
        this.usageService = usageService;
    }

    @Override
    public boolean isValid(ResearchedUsage researchedUsage) {
        UsageStatusEnum status =
            usageService.findStatusByDetailId(Objects.requireNonNull(researchedUsage).getDetailId());
        return null == status || UsageStatusEnum.WORK_RESEARCH == status;
    }

    @Override
    public String getErrorMessage() {
        return "Usage detail doesn’t have WORK_RESEARCH status";
    }
}
