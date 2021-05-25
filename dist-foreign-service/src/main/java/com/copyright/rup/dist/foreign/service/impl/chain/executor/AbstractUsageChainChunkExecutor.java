package com.copyright.rup.dist.foreign.service.impl.chain.executor;

import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;
import com.copyright.rup.dist.foreign.service.api.processor.IUsageJobProcessor;
import com.copyright.rup.dist.foreign.service.impl.chain.JobInfoUtils;

import com.google.common.collect.Iterables;

import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Abstract implementation of {@link IChainExecutor}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/15/2021
 *
 * @param <T> type of items to process
 * @author Uladzislau Shalamitski
 */
abstract class AbstractUsageChainChunkExecutor<T> implements IChainExecutor<T> {

    @Value("$RUP{dist.foreign.usages.chunk_size}")
    private Integer chunkSize;

    private ExecutorService executorService;
    private Map<String, IChainProcessor<List<T>>> productFamilyToProcessorMap;

    @Override
    public JobInfo execute(ChainProcessorTypeEnum type) {
        List<JobInfo> jobInfos = new ArrayList<>();
        productFamilyToProcessorMap.forEach((productFamily, processor) ->
            jobInfos.addAll(execute(productFamily, processor, type)));
        return JobInfoUtils.mergeJobResults(jobInfos);
    }

    @Override
    public void execute(List<T> items, ChainProcessorTypeEnum type) {
        Map<String, List<T>> productFamilyToUsagesMap = items.stream()
            .collect(Collectors.groupingBy(getProductFamilyFunction()));
        productFamilyToUsagesMap.forEach((productFamily, usagesList) -> {
            IChainProcessor<List<T>> processor = productFamilyToProcessorMap.get(productFamily);
            if (Objects.nonNull(processor)) {
                execute(items, processor, type);
            } else {
                throw new UnsupportedOperationException(
                    String.format("Product family %s is not supported", productFamily));
            }
        });
    }

    @Override
    public void execute(Runnable command) {
        executorService.execute(command);
    }

    /**
     * Gets instance of {@link ExecutorService} with 2 threads.
     * Used for sending usages to queues to process them.
     *
     * @return instance of {@link ExecutorService}
     */
    protected ExecutorService getExecutorService() {
        return Executors.newCachedThreadPool();
    }

    /**
     * @return product family to processor map
     */
    abstract Map<String, IChainProcessor<List<T>>> getProductFamilyToProcessorMap();

    /**
     * @return function that returns product family.
     */
    abstract Function<T, String> getProductFamilyFunction();

    /**
     * Post construct method.
     */
    @PostConstruct
    void postConstruct() {
        productFamilyToProcessorMap = getProductFamilyToProcessorMap();
        executorService = getExecutorService();
    }

    /**
     * Pre destroy method.
     */
    @PreDestroy
    void preDestroy() {
        executorService.shutdown();
    }

    private List<JobInfo> execute(String productFamily, IChainProcessor<List<T>> processor,
                                  ChainProcessorTypeEnum type) {
        List<JobInfo> jobInfos = new ArrayList<>();
        if (Objects.nonNull(processor)) {
            if (processor instanceof IUsageJobProcessor && type == processor.getChainProcessorType()) {
                jobInfos.add(((IUsageJobProcessor) processor).jobProcess(productFamily));
            } else {
                jobInfos.addAll(execute(productFamily, processor.getSuccessProcessor(), type));
                jobInfos.addAll(execute(productFamily, processor.getFailureProcessor(), type));
            }
        }
        return jobInfos;
    }

    private void execute(List<T> usages, IChainProcessor<List<T>> processor, ChainProcessorTypeEnum type) {
        if (Objects.nonNull(processor)) {
            if (type == processor.getChainProcessorType()) {
                Iterables.partition(usages, chunkSize).forEach(processor::process);
            } else {
                execute(usages, processor.getSuccessProcessor(), type);
                execute(usages, processor.getFailureProcessor(), type);
            }
        }
    }
}
