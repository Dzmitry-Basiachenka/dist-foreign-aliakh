package com.copyright.rup.dist.foreign.service.impl.quartz;

import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;
import com.copyright.rup.dist.foreign.service.impl.chain.JobInfoUtils;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Arrays;

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
    private IChainExecutor<Usage> usageChainExecutor;
    @Autowired
    @Qualifier("udmUsageChainExecutor")
    private IChainExecutor<UdmUsage> udmChainExecutor;

    /**
     * Runs work matching processors.
     */
    @Override
    public void executeInternal(JobExecutionContext context) {
        JobInfo usageJobInfo = usageChainExecutor.execute(ChainProcessorTypeEnum.MATCHING);
        JobInfo udmJobInfo = udmChainExecutor.execute(ChainProcessorTypeEnum.MATCHING);
        context.setResult(JobInfoUtils.mergeJobResults(Arrays.asList(usageJobInfo, udmJobInfo)));
    }
}
