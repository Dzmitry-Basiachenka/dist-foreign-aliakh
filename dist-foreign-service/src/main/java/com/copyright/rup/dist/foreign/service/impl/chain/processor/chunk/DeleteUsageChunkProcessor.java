package com.copyright.rup.dist.foreign.service.impl.chain.processor.chunk;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Implementation of {@link AbstractUsageChainChunkProcessor} to delete {@link Usage}s.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 02/01/2019
 *
 * @author Pavel Liakh
 * @author Aliaksandr Liakh
 */
public class DeleteUsageChunkProcessor extends AbstractUsageChainChunkProcessor {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IUsageService usageService;

    @Override
    @Profiled(tag = "DeleteUsageChunkProcessor.process")
    public void process(List<Usage> usages) {
        LOGGER.trace("Usage Delete processor. Started. UsageIds={}", LogUtils.ids(usages));
        usages.forEach(usage -> {
            usageService.deleteById(usage.getId());
            LOGGER.trace("Usage Delete processor. Deleted. UsageId={}", usage.getId());
        });
        LOGGER.trace("Usage Delete processor. Finished. UsageIds={}", LogUtils.ids(usages));
    }

    @Override
    public ChainProcessorTypeEnum getChainProcessorType() {
        return ChainProcessorTypeEnum.DELETE;
    }
}
