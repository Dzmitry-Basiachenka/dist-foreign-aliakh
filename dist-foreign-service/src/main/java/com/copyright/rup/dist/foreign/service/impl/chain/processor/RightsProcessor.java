package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.IJobProcessor;
import com.copyright.rup.dist.foreign.service.api.JobProcessorTypeEnum;

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

    @Override
    public void process() {
        /*
            1. Find usages by WORK_FOUND status
            2. Send it to queue via rightsProducer
         */
    }

    @Override
    public void process(Usage usage) {
        /*
            1. Send items to queue via rightsProducer
         */
    }

    @Override
    public JobProcessorTypeEnum getJobProcessorType() {
        return JobProcessorTypeEnum.RIGHTS;
    }
}
