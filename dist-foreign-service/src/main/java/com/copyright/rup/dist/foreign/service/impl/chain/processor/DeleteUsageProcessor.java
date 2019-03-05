package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Implementation of {@link AbstractUsageChainProcessor} to delete usage.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 02/01/19
 *
 * @author Pavel Liakh
 */
public class DeleteUsageProcessor extends AbstractUsageChainProcessor {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IUsageService usageService;

    @Override
    public void process(Usage usage) {
        LOGGER.trace("Usage Delete processor. Started. UsageId={}", usage.getId());
        usageService.deleteById(usage.getId());
        LOGGER.trace("Usage Delete processor. Finished. UsageId={}", usage.getId());
    }

    @Override
    public ChainProcessorTypeEnum getChainProcessorType() {
        return ChainProcessorTypeEnum.DELETE;
    }

    void setUsageService(IUsageService usageService) {
        this.usageService = usageService;
    }
}
