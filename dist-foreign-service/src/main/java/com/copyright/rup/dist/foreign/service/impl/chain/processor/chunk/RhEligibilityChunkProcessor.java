package com.copyright.rup.dist.foreign.service.impl.chain.processor.chunk;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 * Implementation of {@link AbstractUsageJobChunkProcessor} to check whether RH is eligible for distribution or not.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/02/2019
 *
 * @author Uladzislau Shalamitski
 * @author Aliaksandr Liakh
 */
public class RhEligibilityChunkProcessor extends AbstractUsageJobChunkProcessor {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    @Qualifier("df.service.rhEligibilityChunkProducer")
    private IProducer<List<Usage>> rhEligibilityProducer;

    @Override
    @Profiled(tag = "RhEligibilityChunkProcessor.process")
    public void process(List<Usage> usages) {
        LOGGER.trace("Usages RhEligibility processor. Started. UsageIds={}", LogUtils.ids(usages));
        rhEligibilityProducer.send(usages);
        LOGGER.trace("Usages RhEligibility processor. Finished. UsageIds={}", LogUtils.ids(usages));
    }

    @Override
    public ChainProcessorTypeEnum getChainProcessorType() {
        return ChainProcessorTypeEnum.RH_ELIGIBILITY;
    }
}
