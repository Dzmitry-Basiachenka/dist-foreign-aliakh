package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.foreign.domain.AaclClassifiedUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.integration.pi.api.IPiIntegrationService;

/**
 * The validator for Classified AACL usages to check if Wr Wrk Inst exists in PI.
 *
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/23/2020
 *
 * @author Anton Azarenka
 */
public class ClassifiedWrWrkInstValidator implements DistCsvProcessor.IValidator<AaclClassifiedUsage> {

    private final WrWrkInstValidator wrWrkInstValidator;

    /**
     * Constructor.
     *
     * @param piIntegrationService instance of {@link IPiIntegrationService}
     */
    public ClassifiedWrWrkInstValidator(IPiIntegrationService piIntegrationService) {
        this.wrWrkInstValidator = new WrWrkInstValidator(piIntegrationService);
    }

    @Override
    public boolean isValid(AaclClassifiedUsage aaclClassifiedUsage) {
        return wrWrkInstValidator.isValid(convert(aaclClassifiedUsage));
    }

    @Override
    public String getErrorMessage() {
        return wrWrkInstValidator.getErrorMessage();
    }

    private Usage convert(AaclClassifiedUsage aaclClassifiedUsage) {
        Usage usage = new Usage();
        usage.setWrWrkInst(aaclClassifiedUsage.getWrWrkInst());
        return usage;
    }
}
