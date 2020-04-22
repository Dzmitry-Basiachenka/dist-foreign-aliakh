package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;
import com.copyright.rup.dist.foreign.service.impl.chain.executor.IPerformanceLogger;

import java.util.function.Predicate;

/**
 * Wrapper to implement performance logger around {@link IChainProcessor}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 21/04/2020
 *
 * @author Aliaksandr Liakh
 */
public class PerformanceLoggerChainProcessorWrapper implements IChainProcessor<Usage> {

    private final IChainProcessor<Usage> processor;
    private final IPerformanceLogger logger;

    /**
     * Constructor.
     *
     * @param processor instance of {@link IChainProcessor}
     * @param logger    instance of {@link IPerformanceLogger}
     */
    public PerformanceLoggerChainProcessorWrapper(IChainProcessor<Usage> processor, IPerformanceLogger logger) {
        this.processor = processor;
        this.logger = logger;
    }

    @Override
    public void process(Usage usage) {
        processor.process(usage);
        logger.log(getChainProcessorType());
    }

    @Override
    public IChainProcessor<Usage> getSuccessProcessor() {
        return processor.getSuccessProcessor();
    }

    @Override
    public void setSuccessProcessor(IChainProcessor<Usage> successProcessor) {
        processor.setSuccessProcessor(successProcessor);
    }

    @Override
    public IChainProcessor<Usage> getFailureProcessor() {
        return processor.getFailureProcessor();
    }

    @Override
    public void setFailureProcessor(IChainProcessor<Usage> failureProcessor) {
        processor.setFailureProcessor(failureProcessor);
    }

    @Override
    public ChainProcessorTypeEnum getChainProcessorType() {
        return processor.getChainProcessorType();
    }

    @Override
    public void executeNextProcessor(Usage usage, Predicate<Usage> successPredicate) {
        processor.executeNextProcessor(usage, successPredicate);
    }
}

