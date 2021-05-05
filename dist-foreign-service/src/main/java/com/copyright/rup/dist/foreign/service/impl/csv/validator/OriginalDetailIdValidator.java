package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;

/**
 * Validates that Original Detail Id exists in the system.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/04/2021
 *
 * @author Uladzislau Shalamistki
 */
public class OriginalDetailIdValidator implements DistCsvProcessor.IValidator<UdmUsage> {

    private final IUdmUsageService udmUsageService;

    /**
     * Constructor.
     *
     * @param udmUsageService instance of {@link IUdmUsageService}
     */
    public OriginalDetailIdValidator(IUdmUsageService udmUsageService) {
        this.udmUsageService = udmUsageService;
    }

    @Override
    public boolean isValid(UdmUsage udmUsage) {
        return !udmUsageService.isOriginalDetailIdExist(udmUsage.getOriginalDetailId());
    }

    @Override
    public String getErrorMessage() {
        return "Original Detail ID is already present in the system";
    }
}
