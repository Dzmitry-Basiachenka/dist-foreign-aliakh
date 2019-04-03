package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.Work;
import com.copyright.rup.dist.foreign.integration.pi.api.IPiIntegrationService;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * The validator to check if Standard Number exists in PI.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 04/03/19
 *
 * @author Uladzislau Shalamitski
 */
public class StandardNumberValidator implements DistCsvProcessor.IValidator<ResearchedUsage> {

    private final IPiIntegrationService piIntegrationService;

    /**
     * Constructor.
     *
     * @param piIntegrationService instance of {@link IPiIntegrationService}
     */
    public StandardNumberValidator(IPiIntegrationService piIntegrationService) {
        this.piIntegrationService = piIntegrationService;
    }

    @Override
    public boolean isValid(ResearchedUsage researchedUsage) {
        boolean result = true;
        Objects.requireNonNull(researchedUsage);
        Work work = piIntegrationService.findWorkByIdnoAndTitle(researchedUsage.getStandardNumber(), null);
        if (Objects.nonNull(work.getWrWrkInst())) {
            researchedUsage.setStandardNumberType(StringUtils.upperCase(work.getMainIdnoType()));
        } else {
            result = false;
        }
        return result;
    }

    @Override
    public String getErrorMessage() {
        return "Loaded Standard Number is missing in PI or there are multiple matches";
    }
}
