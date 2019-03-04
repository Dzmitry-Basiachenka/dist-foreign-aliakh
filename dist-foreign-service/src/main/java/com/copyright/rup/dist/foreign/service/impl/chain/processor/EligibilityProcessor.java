package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

/**
 * Implementation of {@link AbstractUsageChainProcessor} to make {@link Usage} eligible.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/06/18
 *
 * @author Uladzislau Shalamitski
 */
public class EligibilityProcessor extends AbstractUsageChainProcessor {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IUsageAuditService usageAuditService;

    @Override
    @Transactional
    public void process(Usage usage) {
        LOGGER.trace("Usage Eligibility processor. Started. UsageId={}", usage.getId());
        usageRepository.updateStatus(Collections.singleton(usage.getId()), UsageStatusEnum.ELIGIBLE);
        usageAuditService.logAction(usage.getId(), UsageActionTypeEnum.ELIGIBLE, "Usage has become eligible");
        LOGGER.trace("Usage Eligibility processor. Finished. UsageId={}, UsageStatus=ELIGIBLE", usage.getId());
    }

    @Override
    public ChainProcessorTypeEnum getChainProcessorType() {
        return ChainProcessorTypeEnum.ELIGIBILITY;
    }
}
