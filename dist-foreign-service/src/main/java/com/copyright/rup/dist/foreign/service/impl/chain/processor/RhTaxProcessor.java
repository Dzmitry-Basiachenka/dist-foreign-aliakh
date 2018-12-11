package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.IJobProcessor;
import com.copyright.rup.dist.foreign.service.api.JobProcessorTypeEnum;

import java.util.List;

/**
 * Implementation of {@link AbstractUsageChainProcessor} for applying taxes.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/06/18
 *
 * @author Uladzislau Shalamitski
 */
public class RhTaxProcessor extends AbstractUsageChainProcessor implements IJobProcessor {

    @Override
    public void process() {
        /*
            1. Find NTS usages by RH_FOUND status
            2. Send them to queue via rhTaxProducer
         */
    }

    @Override
    public void process(List<Usage> items) {
        /*
            1. Send incoming items to queue via rhTaxProducer
         */
    }

    @Override
    public JobProcessorTypeEnum getJobProcessorType() {
        return JobProcessorTypeEnum.RH_TAX;
    }
}
