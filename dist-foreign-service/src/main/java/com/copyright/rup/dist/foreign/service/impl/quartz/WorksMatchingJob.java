package com.copyright.rup.dist.foreign.service.impl.quartz;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * Quartz job to get works and setting statuses for {@link Usage}s.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 3/6/18
 *
 * @author Aliaksandr Radkevich
 */
@DisallowConcurrentExecution
@Component
public class WorksMatchingJob extends QuartzJobBean {

    @Autowired
    @Qualifier("usageChainExecutor")
    private IChainExecutor<Usage> chainExecutor;
    @Autowired
    @Qualifier("usageChainChunkExecutor")
    private IChainExecutor<Usage> chainChunkExecutor;
    @Value("$RUP{dist.foreign.usages.chunks}")
    private boolean useChunks;

    /**
     * Runs work matching processors.
     */
    @Override
    public void executeInternal(JobExecutionContext context) {
        context.setResult((useChunks ? chainChunkExecutor : chainExecutor)
            .execute(ChainProcessorTypeEnum.MATCHING));
    }
}
