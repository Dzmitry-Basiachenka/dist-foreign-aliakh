package com.copyright.rup.dist.foreign.service.impl.stm;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.service.api.IStmRhService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link IStmRhService}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/14/19
 *
 * @author Stanislau Rudak
 */
@Component
public class StmRhService implements IStmRhService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IPrmIntegrationService prmIntegrationService;
    @Autowired
    private IUsageService usageService;

    @Override
    public void processStmRh(Usage usage) {
        LOGGER.debug("Processing STM RHs. Started. UsageId={}, RhId={}", usage.getId(),
            usage.getRightsholder().getId());
        boolean isStmRh =
            prmIntegrationService.isStmRightsholder(usage.getRightsholder().getId(), usage.getProductFamily());
        if (!isStmRh) {
            usage.setStatus(UsageStatusEnum.NON_STM_RH);
            usageService.updateProcessedUsage(usage);
        }
        LOGGER.debug("Processing STM RHs. Finished. UsageId={}, RhId={}, IsStmRh={}", usage.getId(),
            usage.getRightsholder().getId(), isStmRh);
    }
}
