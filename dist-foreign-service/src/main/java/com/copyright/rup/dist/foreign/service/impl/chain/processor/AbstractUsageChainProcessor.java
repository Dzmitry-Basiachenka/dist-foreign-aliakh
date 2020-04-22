package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;
import com.copyright.rup.dist.foreign.service.impl.chain.executor.IPerformanceLogger;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Abstract implementation of {@link IChainProcessor}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/06/18
 *
 * @author Uladzislau Shalamitski
 */
public abstract class AbstractUsageChainProcessor implements IChainProcessor<Usage> {

    private IChainProcessor<Usage> successProcessor;
    private IChainProcessor<Usage> failureProcessor;

    @Autowired
    private IPerformanceLogger performanceLogger;

    @Override
    public IChainProcessor<Usage> getSuccessProcessor() {
        return successProcessor;
    }

    @Override
    public void setSuccessProcessor(IChainProcessor<Usage> successProcessor) {
        this.successProcessor = new PerformanceLoggerChainProcessorWrapper(successProcessor, performanceLogger);
    }

    @Override
    public IChainProcessor<Usage> getFailureProcessor() {
        return failureProcessor;
    }

    @Override
    public void setFailureProcessor(IChainProcessor<Usage> failureProcessor) {
        this.failureProcessor = new PerformanceLoggerChainProcessorWrapper(failureProcessor, performanceLogger);
    }
}
