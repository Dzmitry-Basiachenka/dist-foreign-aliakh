package com.copyright.rup.dist.foreign.service.impl.chain.executor;

import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;
import com.copyright.rup.dist.foreign.service.api.processor.IJobProcessor;
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
abstract class AbstractUsageChainExecutor<T> implements IChainExecutor<T> {

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Value("$RUP{dist.foreign.usages.chunk_size}")
    private Integer chunkSize;

    @Override
    public JobInfo execute(ChainProcessorTypeEnum type) {
        List<JobInfo> jobInfos = new ArrayList<>();
        getProductFamilyToProcessorMap().forEach((productFamily, processor) ->
            jobInfos.addAll(execute(productFamily, processor, type)));
        return JobInfoUtils.mergeJobResults(jobInfos);
    }

    @Override
    public void execute(List<T> items, ChainProcessorTypeEnum type) {
        Map<String, List<T>> productFamilyToUsagesMap = items.stream()
            .collect(Collectors.groupingBy(getProductFamilyFunction()));
        productFamilyToUsagesMap.forEach((productFamily, usagesList) -> {
            IChainProcessor<T> processor = getProductFamilyToProcessorMap().get(productFamily);
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
     * @return product family to processor map
     */
    abstract Map<String, IChainProcessor<T>> getProductFamilyToProcessorMap();

    /**
     * @return function that returns product family.
     */
    abstract Function<T, String> getProductFamilyFunction();

    /**
     * Pre destroy method.
     */
    @PreDestroy
    void preDestroy() {
        executorService.shutdown();
    }

    private List<JobInfo> execute(String productFamily, IChainProcessor<T> processor, ChainProcessorTypeEnum type) {
        List<JobInfo> jobInfos = new ArrayList<>();
        if (Objects.nonNull(processor)) {
            if (processor instanceof IJobProcessor && type == processor.getChainProcessorType()) {
                jobInfos.add(((IJobProcessor) processor).jobProcess(productFamily));
            } else {
                jobInfos.addAll(execute(productFamily, processor.getSuccessProcessor(), type));
                jobInfos.addAll(execute(productFamily, processor.getFailureProcessor(), type));
            }
        }
        return jobInfos;
    }

    private void execute(List<T> usages, IChainProcessor<T> processor, ChainProcessorTypeEnum type) {
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
