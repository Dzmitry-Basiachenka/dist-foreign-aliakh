package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.csv.DistCsvProcessor;

import java.util.Objects;

/**
 * The validator for {@link ResearchedUsage}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 03/27/2018
 *
 * @author Aliaksandr Liakh
 */
public class ResearchedUsageValidator implements DistCsvProcessor.IValidator<ResearchedUsage> {

    private final IUsageService usageService;

    /**
     * Constructor.
     *
     * @param usageService instance of {@link IUsageService}
     */
    public ResearchedUsageValidator(IUsageService usageService) {
        this.usageService = usageService;
    }

    @Override
    public boolean isValid(ResearchedUsage researchedUsage) {
        return usageService.isDetailIdExists(Objects.requireNonNull(researchedUsage).getDetailId(),
            UsageStatusEnum.WORK_RESEARCH);
    }

    @Override
    public String getErrorMessage() {
        return "Detail with such ID doesn't exist in the system or usage detail doesn't have WORK_RESEARCH status";
    }
}
