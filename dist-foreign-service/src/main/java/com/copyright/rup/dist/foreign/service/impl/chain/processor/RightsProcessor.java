package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.IJobProcessor;
import com.copyright.rup.dist.foreign.service.api.IRightsService;
import com.copyright.rup.dist.foreign.service.api.JobProcessorTypeEnum;

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
public class RightsProcessor extends AbstractUsageChainProcessor implements IJobProcessor {

    @Autowired
    @Qualifier("df.service.rightsProducer")
    private IProducer<Usage> producer;
    @Autowired
    private IRightsService rightsService;

    @Override
    public void process() {
        rightsService.updateRights();
    }

    @Override
    public void process(Usage usage) {
        producer.send(usage);
    }

    @Override
    public JobProcessorTypeEnum getJobProcessorType() {
        return JobProcessorTypeEnum.RIGHTS;
    }
}
