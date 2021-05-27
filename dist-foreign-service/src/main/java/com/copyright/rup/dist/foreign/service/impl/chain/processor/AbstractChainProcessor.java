package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Abstract implementation of {@link IChainProcessor}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/06/18
 *
 * @param <T> type of item to process
 * @author Uladzislau Shalamitski
 * @author Aliaksandr Liakh
 */
public abstract class AbstractChainProcessor<T> implements IChainProcessor<T> {

    private IChainProcessor<T> successProcessor;
    private IChainProcessor<T> failureProcessor;

    @Override
    public IChainProcessor<T> getSuccessProcessor() {
        return successProcessor;
    }

    @Override
    public void setSuccessProcessor(IChainProcessor<T> successProcessor) {
        this.successProcessor = successProcessor;
    }

    @Override
    public IChainProcessor<T> getFailureProcessor() {
        return failureProcessor;
    }

    @Override
    public void setFailureProcessor(IChainProcessor<T> failureProcessor) {
        this.failureProcessor = failureProcessor;
    }

    @Override
    public void executeNextChainProcessor(List<T> items, Predicate<T> successPredicate) {
        Map<Boolean, List<T>> partitionedItems = items
            .stream()
            .collect(Collectors.partitioningBy(successPredicate));
        List<T> succeedItems = partitionedItems.get(true);
        if (!succeedItems.isEmpty() && Objects.nonNull(getSuccessProcessor())) {
            getSuccessProcessor().process(succeedItems);
        }
        List<T> failedItems = partitionedItems.get(false);
        if (!failedItems.isEmpty() && Objects.nonNull(getFailureProcessor())) {
            getFailureProcessor().process(failedItems);
        }
    }
}
