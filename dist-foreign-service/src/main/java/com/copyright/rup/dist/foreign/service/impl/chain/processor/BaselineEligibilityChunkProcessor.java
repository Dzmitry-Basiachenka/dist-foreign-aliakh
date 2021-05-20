package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import org.apache.commons.collections4.CollectionUtils;
import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of {@link AbstractChainChunkProcessor} to make {@link Usage}s eligible
 * if they have not-null baselineId.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 02/28/2020
 *
 * @author Anton Azarenka
 * @author Aliaksandr Liakh
 */
public class BaselineEligibilityChunkProcessor extends AbstractChainChunkProcessor<Usage> {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IUsageAuditService usageAuditService;

    @Override
    @Transactional
    @Profiled(tag = "BaselineEligibilityChunkProcessor.process")
    public void process(List<Usage> usages) {
        LOGGER.trace("Usages Baseline Eligibility processor. Started. UsageIds={}", LogUtils.ids(usages));
        Set<String> usageIds = usages
            .stream()
            .filter(usage -> Objects.nonNull(usage.getAaclUsage().getBaselineId()))
            .map(Usage::getId)
            .collect(Collectors.toSet());
        if (CollectionUtils.isNotEmpty(usageIds)) {
            usageRepository.updateStatus(usageIds, UsageStatusEnum.ELIGIBLE);
            usageAuditService.logAction(usageIds, UsageActionTypeEnum.ELIGIBLE, "Usage has become eligible");
        }
        LOGGER.trace("Usages Baseline Eligibility processor. Finished. UsageIds={}", LogUtils.ids(usages));
    }

    @Override
    public ChainProcessorTypeEnum getChainProcessorType() {
        return ChainProcessorTypeEnum.ELIGIBILITY;
    }
}
