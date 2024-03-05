package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.util.LogUtils;
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

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of {@link AbstractChainProcessor} to make {@link Usage} eligible.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/06/2018
 *
 * @author Uladzislau Shalamitski
 * @author Aliaksandr Liakh
 */
public class EligibilityProcessor extends AbstractChainProcessor<Usage> {

    private static final long serialVersionUID = 4141527778443320443L;
    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IUsageAuditService usageAuditService;

    @Override
    @Transactional
    @Profiled(tag = "EligibilityProcessor.process")
    public void process(List<Usage> usages) {
        LOGGER.trace("Usages Eligibility processor. Started. UsageIds={}", LogUtils.ids(usages));
        Set<String> usageIds = usages
            .stream()
            .map(Usage::getId)
            .collect(Collectors.toSet());
        usageRepository.updateStatus(usageIds, UsageStatusEnum.ELIGIBLE);
        usageAuditService.logAction(usageIds, UsageActionTypeEnum.ELIGIBLE, "Usage has become eligible");
        LOGGER.trace("Usages Eligibility processor. Finished. UsageIds={}", LogUtils.ids(usages));
    }

    @Override
    public ChainProcessorTypeEnum getChainProcessorType() {
        return ChainProcessorTypeEnum.ELIGIBILITY;
    }
}
