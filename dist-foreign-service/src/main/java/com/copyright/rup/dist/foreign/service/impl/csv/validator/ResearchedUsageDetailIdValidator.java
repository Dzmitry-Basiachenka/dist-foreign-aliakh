package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.csv.DistCsvProcessor;

import java.util.Objects;

/**
 * The validator for detail id of {@link ResearchedUsage}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 03/27/2018
 *
 * @author Aliaksandr Liakh
 */
public class ResearchedUsageDetailIdValidator implements DistCsvProcessor.IValidator<ResearchedUsage> {

    private final IUsageService usageService;

    /**
     * Constructor.
     *
     * @param usageService instance of {@link IUsageService}
     */
    public ResearchedUsageDetailIdValidator(IUsageService usageService) {
        this.usageService = usageService;
    }

    @Override
    public boolean isValid(ResearchedUsage researchedUsage) {
        return 1 == usageService.findCountByDetailId(Objects.requireNonNull(researchedUsage).getDetailId());
    }

    @Override
    public String getErrorMessage() {
        return "Detail ID: Detail with such ID doesn’t exist in the system";
    }
}
