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
 * Quartz job to send NTS usages in RH_FOUND status to RH Tax queue for further processing.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/04/18
 *
 * @author Pavel Liakh
 */
@DisallowConcurrentExecution
@Component
public class RhTaxJob extends QuartzJobBean {

    @Autowired
    @Qualifier("usageChainExecutor")
    private IChainExecutor<Usage> chainExecutor;

    /**
     * Finds NTS usages in RH_FOUND status and send to RH Tax queue.
     */
    @Override
    public void executeInternal(JobExecutionContext context) {
        context.setResult(chainExecutor.execute(ChainProcessorTypeEnum.RH_TAX));
    }
}
