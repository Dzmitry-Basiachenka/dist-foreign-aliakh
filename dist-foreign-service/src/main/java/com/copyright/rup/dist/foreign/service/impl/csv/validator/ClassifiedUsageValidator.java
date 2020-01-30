package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.foreign.domain.AaclClassifiedUsage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import java.util.Objects;

/**
 * The validator for {@link AaclClassifiedUsage}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 01/24/2020
 *
 * @author Anton Azarenka
 */
public class ClassifiedUsageValidator implements DistCsvProcessor.IValidator<AaclClassifiedUsage> {

    private final IUsageService usageService;

    /**
     * Constructor.
     *
     * @param usageService instance of {@link IUsageService}
     */
    public ClassifiedUsageValidator(IUsageService usageService) {
        this.usageService = usageService;
    }

    @Override
    public boolean isValid(AaclClassifiedUsage usage) {
        return usageService.isUsageIdExists(Objects.requireNonNull(usage).getDetailId(),
            UsageStatusEnum.WORK_RESEARCH);
    }

    @Override
    public String getErrorMessage() {
        return "Detail with such ID doesn't exist in the system";
    }
}
