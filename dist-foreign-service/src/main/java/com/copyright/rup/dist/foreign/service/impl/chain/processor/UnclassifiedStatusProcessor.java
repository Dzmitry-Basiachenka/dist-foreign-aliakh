package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of {@link AbstractChainProcessor} to make {@link Usage}s unclassified.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/21/19
 *
 * @author Pavel Liakh
 * @author Aliaksandr Liakh
 */
public class UnclassifiedStatusProcessor extends AbstractChainProcessor<Usage> {

    private static final long serialVersionUID = 954874539690089156L;
    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IUsageRepository usageRepository;

    @Override
    @Profiled(tag = "UnclassifiedStatusProcessor.process")
    public void process(List<Usage> usages) {
        LOGGER.trace("Usages UnclassifiedStatus processor. Started. UsageIds={}", LogUtils.ids(usages));
        Set<String> usageIds = usages
            .stream()
            .map(Usage::getId)
            .collect(Collectors.toSet());
        usageRepository.updateStatus(usageIds, UsageStatusEnum.UNCLASSIFIED);
        LOGGER.trace("Usages UnclassifiedStatus processor. Finished. UsageIds={}", LogUtils.ids(usages));
    }

    @Override
    public ChainProcessorTypeEnum getChainProcessorType() {
        return ChainProcessorTypeEnum.CLASSIFICATION;
    }
}
