package com.copyright.rup.dist.foreign.service.impl.chain.processor.chunk;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;
import com.copyright.rup.dist.foreign.service.api.processor.chunk.IChainChunkProcessor;
import com.copyright.rup.dist.foreign.service.impl.chain.executor.IPerformanceLogger;
import com.copyright.rup.dist.foreign.service.impl.chain.processor.PerformanceLoggerChainProcessorWrapper;

import org.springframework.beans.factory.annotation.Autowired;

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
 * @author Uladzislau Shalamitski
 * @author Aliaksandr Liakh
 */
abstract class AbstractUsageChainChunkProcessor implements IChainChunkProcessor<List<Usage>, Usage> {

    private IChainProcessor<List<Usage>> successProcessor;
    private IChainProcessor<List<Usage>> failureProcessor;

    @Autowired
    private IPerformanceLogger performanceLogger;

    @Override
    public IChainProcessor<List<Usage>> getSuccessProcessor() {
        return successProcessor;
    }

    @Override
    public void setSuccessProcessor(IChainProcessor<List<Usage>> successProcessor) {
        this.successProcessor = new PerformanceLoggerChainProcessorWrapper<>(successProcessor, performanceLogger,
            List::size);
    }

    @Override
    public IChainProcessor<List<Usage>> getFailureProcessor() {
        return failureProcessor;
    }

    @Override
    public void setFailureProcessor(IChainProcessor<List<Usage>> failureProcessor) {
        this.failureProcessor = new PerformanceLoggerChainProcessorWrapper<>(failureProcessor, performanceLogger,
            List::size);
    }

    @Override
    public void executeNextChainProcessor(List<Usage> usages, Predicate<Usage> successPredicate) {
        Map<Boolean, List<Usage>> partitionedUsages = usages
            .stream()
            .collect(Collectors.partitioningBy(successPredicate));
        List<Usage> succeedUsages = partitionedUsages.get(true);
        if (!succeedUsages.isEmpty() && Objects.nonNull(getSuccessProcessor())) {
            getSuccessProcessor().process(succeedUsages);
        }
        List<Usage> failedUsages = partitionedUsages.get(false);
        if (!failedUsages.isEmpty() && Objects.nonNull(getFailureProcessor())) {
            getFailureProcessor().process(failedUsages);
        }
    }
}
