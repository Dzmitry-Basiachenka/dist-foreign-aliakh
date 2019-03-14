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
 * Implementation of {@link AbstractUsageChainProcessor} to process RH Tax.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/02/19
 *
 * @author Pavel Liakh
 */
public class RhTaxProcessor extends AbstractUsageJobProcessor {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    @Qualifier("df.service.rhTaxProducer")
    private IProducer<Usage> rhTaxProducer;

    @Override
    @Profiled(tag = "RhTaxProcessor.process")
    public void process(Usage usage) {
        LOGGER.trace("Usage RhTax processor. Started. UsageId={}", usage.getId());
        rhTaxProducer.send(usage);
        LOGGER.trace("Usage RhTax processor. Finished. UsageId={}", usage.getId());
    }

    @Override
    public ChainProcessorTypeEnum getChainProcessorType() {
        return ChainProcessorTypeEnum.RH_TAX;
    }

    void setRhTaxProducer(IProducer<Usage> rhTaxProducer) {
        this.rhTaxProducer = rhTaxProducer;
    }
}
