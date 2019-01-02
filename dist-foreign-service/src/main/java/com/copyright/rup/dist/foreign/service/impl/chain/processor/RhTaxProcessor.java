package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.ChainProcessorTypeEnum;

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
public class RhTaxProcessor extends AbstractUsageChainProcessor {

    @Autowired
    @Qualifier("df.service.rhTaxProducer")
    private IProducer<Usage> rhTaxProducer;

    @Override
    public void process(Usage item) {
        rhTaxProducer.send(item);
    }

    @Override
    public ChainProcessorTypeEnum getChainProcessorType() {
        return ChainProcessorTypeEnum.RH_TAX;
    }

    void setRhTaxProducer(IProducer<Usage> rhTaxProducer) {
        this.rhTaxProducer = rhTaxProducer;
    }
}
