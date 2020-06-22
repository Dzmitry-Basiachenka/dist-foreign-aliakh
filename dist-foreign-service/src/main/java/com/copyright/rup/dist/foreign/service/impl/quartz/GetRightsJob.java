package com.copyright.rup.dist.foreign.service.impl.quartz;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

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

    @Override
    public void executeInternal(JobExecutionContext context) {
        context.setResult(chainExecutor.execute(ChainProcessorTypeEnum.RIGHTS));
    }
}
