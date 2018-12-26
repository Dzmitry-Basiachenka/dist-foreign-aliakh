package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.ChainProcessorTypeEnum;
import com.copyright.rup.dist.foreign.service.api.IUsageJobProcessor;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

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
public class MatchingProcessor extends AbstractUsageChainProcessor implements IUsageJobProcessor {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    @Qualifier("df.service.matchingProducer")
    private IProducer<Usage> matchingProducer;
    @Autowired
    private IUsageService usageService;

    @Override
    public void process(String productFamily) {
        List<Usage> usages = usageService.getUsagesByStatusAndProductFamily(UsageStatusEnum.NEW, productFamily);
        LOGGER.info("Send usages for works matching. Started. ProductFamily={}, UsagesCount={}", productFamily,
            LogUtils.size(usages));
        usages.forEach(this::process);
        LOGGER.info("Send usages for works matching. Finished. ProductFamily={}, UsagesCount={}", productFamily,
            LogUtils.size(usages));
    }

    @Override
    public void process(Usage usage) {
        matchingProducer.send(usage);
    }

    @Override
    public ChainProcessorTypeEnum getChainProcessorType() {
        return ChainProcessorTypeEnum.MATCHING;
    }
}
