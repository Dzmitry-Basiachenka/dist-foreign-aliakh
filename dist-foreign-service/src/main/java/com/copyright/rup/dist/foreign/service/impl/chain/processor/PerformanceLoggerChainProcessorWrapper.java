package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;
import com.copyright.rup.dist.foreign.service.impl.chain.executor.IPerformanceLogger;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Wrapper to implement performance logger around {@link IChainProcessor}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 21/04/2020
 *
 * @param <T> type of items to process
 * @author Aliaksandr Liakh
 */
public class PerformanceLoggerChainProcessorWrapper<T> implements IChainProcessor<T> {

    private final IChainProcessor<T> processor;
    private final IPerformanceLogger logger;
    private final Function<T, Integer> incrementFunction;

    /**
     * Constructor.
     *
     * @param processor         instance of {@link IChainProcessor}
     * @param logger            instance of {@link IPerformanceLogger}
     * @param incrementFunction {@link Function} to get increment from processed item
     */
    public PerformanceLoggerChainProcessorWrapper(IChainProcessor<T> processor, IPerformanceLogger logger,
                                                  Function<T, Integer> incrementFunction) {
        this.processor = processor;
        this.logger = logger;
        this.incrementFunction = incrementFunction;
    }

    @Override
    public void process(T item) {
        processor.process(item);
        logger.log(getChainProcessorType(), incrementFunction.apply(item));
    }

    @Override
    public IChainProcessor<T> getSuccessProcessor() {
        return processor.getSuccessProcessor();
    }

    @Override
    public void setSuccessProcessor(IChainProcessor<T> successProcessor) {
        processor.setSuccessProcessor(successProcessor);
    }

    @Override
    public IChainProcessor<T> getFailureProcessor() {
        return processor.getFailureProcessor();
    }

    @Override
    public void setFailureProcessor(IChainProcessor<T> failureProcessor) {
        processor.setFailureProcessor(failureProcessor);
    }

    @Override
    public ChainProcessorTypeEnum getChainProcessorType() {
        return processor.getChainProcessorType();
    }

    @Override
    public void executeNextProcessor(T item, Predicate<T> successPredicate) {
        processor.executeNextProcessor(item, successPredicate);
    }
}

