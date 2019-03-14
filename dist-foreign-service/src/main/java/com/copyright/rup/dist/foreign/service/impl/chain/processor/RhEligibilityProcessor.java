package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Implementation of {@link AbstractUsageChainProcessor} to check whether RH is eligible for distribution or not.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/02/19
 *
 * @author Uladzislau Shalamitski
 */
public class RhEligibilityProcessor extends AbstractUsageJobProcessor {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    @Qualifier("df.service.rhEligibilityProducer")
    private IProducer<Usage> rhEligibilityProducer;

    @Override
    @Profiled(tag = "RhEligibilityProcessor.process")
    public void process(Usage usage) {
        LOGGER.trace("Usage RhEligibility processor. Started. UsageId={}", usage.getId());
        rhEligibilityProducer.send(usage);
        LOGGER.trace("Usage RhEligibility processor. Finished. UsageId={}", usage.getId());
    }

    @Override
    public ChainProcessorTypeEnum getChainProcessorType() {
        return ChainProcessorTypeEnum.RH_ELIGIBILITY;
    }

    void setRhEligibilityProducer(IProducer<Usage> rhEligibilityProducer) {
        this.rhEligibilityProducer = rhEligibilityProducer;
    }
}
