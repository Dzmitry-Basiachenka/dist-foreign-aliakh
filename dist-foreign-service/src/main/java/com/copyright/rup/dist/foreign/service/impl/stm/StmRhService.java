package com.copyright.rup.dist.foreign.service.impl.stm;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.stm.IStmRhService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of {@link IStmRhService}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/14/2019
 *
 * @author Stanislau Rudak
 * @author Aliaksandr Liakh
 */
@Component
public class StmRhService implements IStmRhService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IPrmIntegrationService prmIntegrationService;
    @Autowired
    private IUsageService usageService;

    @Override
    public void processStmRhs(List<Usage> usages, String productFamily) {
        LOGGER.debug("Processing STM RHs. Started. UsageIds={}", LogUtils.ids(usages));
        Set<String> rhIds = usages
            .stream()
            .map(usage -> usage.getRightsholder().getId())
            .collect(Collectors.toSet());
        Map<String, Boolean> rhIdsToIsStmRhs =
            prmIntegrationService.getStmRightsholderPreferenceMap(rhIds, productFamily);
        usages.forEach(usage -> {
            boolean isStmRh = rhIdsToIsStmRhs.get(usage.getRightsholder().getId());
            LOGGER.debug("Processing STM RHs. Processed. UsageId={}, RhId={}, IsStmRh={}", usage.getId(),
                usage.getRightsholder().getId(), isStmRh);
            if (!isStmRh) {
                usage.setStatus(UsageStatusEnum.NON_STM_RH);
                usageService.updateProcessedUsage(usage);
            }
        });
        LOGGER.debug("Processing STM RHs. Finished. UsageIds={}", LogUtils.ids(usages));
    }
}
