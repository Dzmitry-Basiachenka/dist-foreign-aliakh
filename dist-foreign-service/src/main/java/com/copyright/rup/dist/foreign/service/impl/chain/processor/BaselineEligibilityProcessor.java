package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

/**
 * Implementation of {@link AbstractUsageChainProcessor} to make {@link Usage} eligible.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 02/28/20
 *
 * @author Anton Azarenka
 */
public class BaselineEligibilityProcessor extends AbstractUsageChainProcessor {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IUsageAuditService usageAuditService;

    @Override
    @Transactional
    @Profiled(tag = "BaselineEligibilityProcessor.process")
    public void process(Usage usage) {
        //todo {aazarenka} recheck is it works
        if (null != usage.getAaclUsage().getBaselineId()) {
            LOGGER.trace("Usage Baseline Eligibility processor. Started. UsageId={}", usage.getId());
            usageRepository.updateStatus(Collections.singleton(usage.getId()), UsageStatusEnum.ELIGIBLE);
            usageAuditService.logAction(usage.getId(), UsageActionTypeEnum.ELIGIBLE, "Usage has become eligible");
            LOGGER.trace("Usage Baseline Eligibility processor. Finished. UsageId={}, UsageStatus=ELIGIBLE",
                usage.getId());
        }
    }

    @Override
    public ChainProcessorTypeEnum getChainProcessorType() {
        return ChainProcessorTypeEnum.ELIGIBILITY;
    }
}
