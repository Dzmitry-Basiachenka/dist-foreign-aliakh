package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Implementation of {@link AbstractUsageChainProcessor} for getting rights.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/06/18
 *
 * @author Uladzislau Shalamitski
 */
public class RightsProcessor extends AbstractUsageJobProcessor {

    @Autowired
    @Qualifier("df.service.rightsProducer")
    private IProducer<Usage> producer;

    @Override
    public void process(Usage usage) {
        producer.send(usage);
    }

    @Override
    public ChainProcessorTypeEnum getChainProcessorType() {
        return ChainProcessorTypeEnum.RIGHTS;
    }

    void setProducer(IProducer<Usage> producer) {
        this.producer = producer;
    }
}
