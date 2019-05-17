package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.integration.pi.api.IPiIntegrationService;

/**
 * The validator for researched usages to check if Wr Wrk Inst exists in PI.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 04/03/19
 *
 * @author Uladzislau Shalamitski
 */
public class ResearchedWrWrkInstValidator implements DistCsvProcessor.IValidator<ResearchedUsage> {

    private final WrWrkInstValidator wrWrkInstValidator;

    /**
     * Constructor.
     *
     * @param piIntegrationService instance of {@link IPiIntegrationService}
     */
    public ResearchedWrWrkInstValidator(IPiIntegrationService piIntegrationService) {
        this.wrWrkInstValidator = new WrWrkInstValidator(piIntegrationService);
    }

    @Override
    public boolean isValid(ResearchedUsage researchedUsage) {
        return wrWrkInstValidator.isValid(convert(researchedUsage));
    }

    @Override
    public String getErrorMessage() {
        return wrWrkInstValidator.getErrorMessage();
    }

    private Usage convert(ResearchedUsage researchedUsage) {
        Usage usage = new Usage();
        usage.setWrWrkInst(researchedUsage.getWrWrkInst());
        return usage;
    }
}
