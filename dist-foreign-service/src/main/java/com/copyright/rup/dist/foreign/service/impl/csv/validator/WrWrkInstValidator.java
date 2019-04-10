package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.Work;
import com.copyright.rup.dist.foreign.integration.pi.api.IPiIntegrationService;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * The validator to check if Wr Wrk Inst exists in PI.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 04/01/19
 *
 * @author Uladzislau Shalamitski
 */
public class WrWrkInstValidator implements DistCsvProcessor.IValidator<Usage> {

    private final IPiIntegrationService piIntegrationService;

    /**
     * Constructor.
     *
     * @param piIntegrationService instance of {@link IPiIntegrationService}
     */
    public WrWrkInstValidator(IPiIntegrationService piIntegrationService) {
        this.piIntegrationService = piIntegrationService;
    }

    @Override
    public boolean isValid(Usage usage) {
        boolean result = true;
        if (Objects.nonNull(Objects.requireNonNull(usage).getWrWrkInst())
            && StringUtils.isBlank(usage.getStandardNumberType())) {
            Work work = piIntegrationService.findWorkByWrWrkInst(usage.getWrWrkInst());
            if (Objects.isNull(work.getWrWrkInst())) {
                result = false;
            }
        }
        return result;
    }

    @Override
    public String getErrorMessage() {
        return "Loaded Wr Wrk Inst is missing in PI";
    }
}
