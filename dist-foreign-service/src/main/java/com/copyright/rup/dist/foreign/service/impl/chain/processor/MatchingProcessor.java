package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IJobProcessor;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.JobProcessorTypeEnum;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 * Implementation of {@link AbstractUsageChainProcessor} for searching for works.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/06/18
 *
 * @author Uladzislau Shalamitski
 */
public class MatchingProcessor extends AbstractUsageChainProcessor implements IJobProcessor {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    @Qualifier("df.service.matchingProducer")
    private IProducer<Usage> matchingProducer;
    @Autowired
    private IUsageService usageService;

    @Override
    public void process() {
        List<Usage> usages = usageService.getUsagesByStatus(UsageStatusEnum.NEW);
        LOGGER.info("Send usages for works matching. Started. UsagesCount={}", LogUtils.size(usages));
        usages.forEach(this::process);
        LOGGER.info("Send usages for works matching. Finished. UsagesCount={}", LogUtils.size(usages));
    }

    @Override
    public void process(Usage usage) {
        matchingProducer.send(usage);
    }

    @Override
    public JobProcessorTypeEnum getJobProcessorType() {
        return JobProcessorTypeEnum.MATCHING;
    }
}
