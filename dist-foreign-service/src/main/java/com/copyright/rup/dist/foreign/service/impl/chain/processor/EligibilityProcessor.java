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
 * Implementation of {@link AbstractUsageChainProcessor} to make {@link Usage} eligible.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/06/18
 *
 * @author Uladzislau Shalamitski
 */
public class EligibilityProcessor extends AbstractUsageChainProcessor {

    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IUsageAuditService usageAuditService;

    private final String auditReason;

    /**
     * Constructor.
     *
     * @param auditReason audit reason for making usage eligible
     */
    public EligibilityProcessor(String auditReason) {
        this.auditReason = auditReason;
    }

    @Override
    @Transactional
    public void process(Usage usage) {
        usageRepository.updateStatus(Collections.singleton(usage.getId()), UsageStatusEnum.ELIGIBLE);
        usageAuditService.logAction(usage.getId(), UsageActionTypeEnum.ELIGIBLE, auditReason);
    }

    @Override
    public ChainProcessorTypeEnum getChainProcessorType() {
        return ChainProcessorTypeEnum.ELIGIBILITY;
    }
}
