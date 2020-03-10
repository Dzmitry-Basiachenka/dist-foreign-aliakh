package com.copyright.rup.dist.foreign.service.impl.chain.executor;

import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.common.domain.job.JobStatusEnum;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;
import com.copyright.rup.dist.foreign.service.api.processor.IUsageJobProcessor;

import com.google.common.collect.ImmutableMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Implementation of {@link IChainExecutor}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/06/18
 *
 * @author Uladzislau Shalamitski
 */
@Component
public class UsageChainExecutor implements IChainExecutor<Usage> {

    @Autowired
    @Qualifier("df.service.fasMatchingProcessor")
    private IChainProcessor<Usage> fasProcessor;
    @Autowired
    @Qualifier("df.service.ntsRightsProcessor")
    private IChainProcessor<Usage> ntsProcessor;
    @Autowired
    @Qualifier("df.service.aaclMatchingProcessor")
    private IChainProcessor<Usage> aaclProcessor;

    private ExecutorService executorService;

    private Map<String, IChainProcessor<Usage>> productFamilyToProcessorMap;

    /**
     * Initialization.
     */
    @PostConstruct
    public void init() {
        productFamilyToProcessorMap = ImmutableMap.of(
            FdaConstants.FAS_PRODUCT_FAMILY, fasProcessor,
            FdaConstants.CLA_FAS_PRODUCT_FAMILY, fasProcessor,
            FdaConstants.NTS_PRODUCT_FAMILY, ntsProcessor,
            FdaConstants.AACL_PRODUCT_FAMILY, aaclProcessor);
    }

    @Override
    public JobInfo execute(ChainProcessorTypeEnum type) {
        List<JobInfo> jobInfos = new ArrayList<>();
        productFamilyToProcessorMap.forEach((productFamily, processor) ->
            jobInfos.addAll(execute(productFamily, processor, type)));
        return mergeJobResults(jobInfos);
    }

    @Override
    public void execute(List<Usage> usages, ChainProcessorTypeEnum type) {
        Map<String, List<Usage>> productFamilyToUsagesMap = usages.stream()
            .collect(Collectors.groupingBy(Usage::getProductFamily));
        productFamilyToUsagesMap.forEach((productFamily, usagesList) -> {
            IChainProcessor<Usage> processor = productFamilyToProcessorMap.get(productFamily);
            if (Objects.nonNull(processor)) {
                execute(usages, processor, type);
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
     * Post construct method.
     */
    @PostConstruct
    void postConstruct() {
        executorService = getExecutorService();
    }

    /**
     * Pre destroy method.
     */
    @PreDestroy
    void preDestroy() {
        executorService.shutdown();
    }

    private List<JobInfo> execute(String productFamily, IChainProcessor<Usage> processor, ChainProcessorTypeEnum type) {
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

    private void execute(List<Usage> usages, IChainProcessor<Usage> processor, ChainProcessorTypeEnum type) {
        if (Objects.nonNull(processor)) {
            if (type == processor.getChainProcessorType()) {
                usages.forEach(processor::process);
            } else {
                execute(usages, processor.getSuccessProcessor(), type);
                execute(usages, processor.getFailureProcessor(), type);
            }
        }
    }

    private JobInfo mergeJobResults(List<JobInfo> jobInfos) {
        JobStatusEnum status = jobInfos.stream().anyMatch(jobInfo -> JobStatusEnum.FINISHED == jobInfo.getStatus())
            ? JobStatusEnum.FINISHED : JobStatusEnum.SKIPPED;
        String result = jobInfos.stream()
            .map(JobInfo::getResult)
            .collect(Collectors.joining("; "));
        return new JobInfo(status, result);
    }
}
