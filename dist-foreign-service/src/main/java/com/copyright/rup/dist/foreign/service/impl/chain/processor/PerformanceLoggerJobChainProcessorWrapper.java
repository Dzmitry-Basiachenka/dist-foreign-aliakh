package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;
import com.copyright.rup.dist.foreign.service.api.processor.IUsageJobProcessor;
import com.copyright.rup.dist.foreign.service.impl.chain.executor.IPerformanceLogger;

import java.util.function.Function;

/**
 * Wrapper to implement performance logger around {@link IUsageJobProcessor}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 5/5/2020
 *
 * @param <T> type of items to process
 * @author Stanislau Rudak
 */
public class PerformanceLoggerJobChainProcessorWrapper<T> extends PerformanceLoggerChainProcessorWrapper<T>
    implements IUsageJobProcessor {

    /**
     * Constructor.
     *
     * @param processor         instance of {@link IChainProcessor}
     * @param logger            instance of {@link IPerformanceLogger}
     * @param incrementFunction {@link Function} to get increment from processed item
     */
    public PerformanceLoggerJobChainProcessorWrapper(IChainProcessor<T> processor, IPerformanceLogger logger,
                                                     Function<T, Integer> incrementFunction) {
        super(processor, logger, incrementFunction);
    }

    @Override
    public JobInfo jobProcess(String productFamily) {
        return ((IUsageJobProcessor) getDelegate()).jobProcess(productFamily);
    }
}
