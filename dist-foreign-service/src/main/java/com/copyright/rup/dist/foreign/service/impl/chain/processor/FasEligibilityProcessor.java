package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.ChainProcessorTypeEnum;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

/**
 * Implementation of {@link AbstractUsageChainProcessor} to make FAS/FAS2 {@link Usage} eligible.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/06/18
 *
 * @author Uladzislau Shalamitski
 */
public class FasEligibilityProcessor extends AbstractUsageChainProcessor {

    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IUsageAuditService usageAuditService;

    @Override
    @Transactional
    public void process(Usage usage) {
        usageRepository.updateStatus(Collections.singleton(usage.getId()), UsageStatusEnum.ELIGIBLE);
        usageAuditService.logAction(usage.getId(), UsageActionTypeEnum.ELIGIBLE, "Usage has become eligible");
    }

    @Override
    public ChainProcessorTypeEnum getChainProcessorType() {
        return ChainProcessorTypeEnum.ELIGIBILITY;
    }
}
