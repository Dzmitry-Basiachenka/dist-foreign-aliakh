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
 * Quartz job to get grants from RMS.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 04/24/2018
 *
 * @author Aliaksandr Liakh
 */
@DisallowConcurrentExecution
@Component
public class GetRightsJob extends QuartzJobBean {

    @Autowired
    @Qualifier("usageChainChunkExecutor")
    private IChainExecutor<Usage> chainExecutor;
    @Autowired
    @Qualifier("udmUsageChainChunkExecutor")
    private IChainExecutor<UdmUsage> udmChainExecutor;

    @Override
    public void executeInternal(JobExecutionContext context) {
        JobInfo usageJobInfo = chainExecutor.execute(ChainProcessorTypeEnum.RIGHTS);
        JobInfo udmJobInfo = udmChainExecutor.execute(ChainProcessorTypeEnum.RIGHTS);
        context.setResult(JobInfoUtils.mergeJobResults(Arrays.asList(usageJobInfo, udmJobInfo)));
    }
}
