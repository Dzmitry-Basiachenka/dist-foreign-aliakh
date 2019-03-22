package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

/**
 * Implementation of {@link AbstractUsageChainProcessor} to set {@link UsageStatusEnum#UNCLASSIFIED} status to
 * {@link Usage}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/21/19
 *
 * @author Pavel Liakh
 */
public class UnclassifiedStatusProcessor extends AbstractUsageChainProcessor {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IUsageRepository usageRepository;

    @Override
    @Profiled(tag = "UnclassifiedStatusProcessor.process")
    public void process(Usage usage) {
        LOGGER.trace("Usage UnclassifiedStatus processor. Started. UsageId={}", usage.getId());
        usageRepository.updateStatus(Collections.singleton(usage.getId()), UsageStatusEnum.UNCLASSIFIED);
        LOGGER.trace("Usage UnclassifiedStatus processor. Finished. UsageId={}", usage.getId());
    }

    @Override
    public ChainProcessorTypeEnum getChainProcessorType() {
        return ChainProcessorTypeEnum.CLASSIFICATION;
    }

    void setUsageRepository(IUsageRepository usageRepository) {
        this.usageRepository = usageRepository;
    }
}
