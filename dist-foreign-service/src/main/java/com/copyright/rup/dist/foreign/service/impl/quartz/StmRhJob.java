package com.copyright.rup.dist.foreign.service.impl.quartz;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * Quartz job to send NTS usages in US_TAX_COUNTRY status to STM RH queue for further processing.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/14/19
 *
 * @author Stanislau Rudak
 */
@DisallowConcurrentExecution
@Component
public class StmRhJob extends QuartzJobBean {

    @Autowired
    private IChainExecutor<Usage> executor;

    /**
     * Finds NTS usages in US_TAX_COUNTRY status and send to STM RH queue.
     */
    @Override
    public void executeInternal(JobExecutionContext context) {
        context.setResult(executor.execute(ChainProcessorTypeEnum.STM_RH));
    }
}
