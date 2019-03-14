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
 * Implementation of {@link AbstractUsageChainProcessor} for searching for works.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/06/18
 *
 * @author Uladzislau Shalamitski
 */
public class MatchingProcessor extends AbstractUsageJobProcessor {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    @Qualifier("df.service.matchingProducer")
    private IProducer<Usage> matchingProducer;

    @Override
    @Profiled(tag = "MatchingProcessor.process")
    public void process(Usage usage) {
        LOGGER.trace("Usage Matching processor. Started. UsageId={}", usage.getId());
        matchingProducer.send(usage);
        LOGGER.trace("Usage Matching processor. Finished. UsageId={}", usage.getId());
    }

    @Override
    public ChainProcessorTypeEnum getChainProcessorType() {
        return ChainProcessorTypeEnum.MATCHING;
    }

    void setMatchingProducer(IProducer<Usage> matchingProducer) {
        this.matchingProducer = matchingProducer;
    }
}
