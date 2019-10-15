package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Implementation of {@link AbstractUsageChainProcessor} for processing STM RH usages.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/10/19
 *
 * @author Stanislau Rudak
 */
public class StmRhProcessor extends AbstractUsageJobProcessor {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    @Qualifier("df.service.stmRhProducer")
    private IProducer<Usage> producer;

    @Override
    public void process(Usage usage) {
        LOGGER.trace("Usage STM RH processor. Started. UsageId={}", usage.getId());
        producer.send(usage);
        LOGGER.trace("Usage STM RH processor. Finished. UsageId={}", usage.getId());
    }

    @Override
    public ChainProcessorTypeEnum getChainProcessorType() {
        return ChainProcessorTypeEnum.STM_RH;
    }
}
