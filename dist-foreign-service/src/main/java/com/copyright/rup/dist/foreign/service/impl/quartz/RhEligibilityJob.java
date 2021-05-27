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
 * Quartz job to send NTS usages in NON_STM_RH status to RH Eligibility queue for further processing.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 01/04/19
 *
 * @author Uladzislau Shalamitski
 */
@DisallowConcurrentExecution
@Component
public class RhEligibilityJob extends QuartzJobBean {

    @Autowired
    @Qualifier("usageChainExecutor")
    private IChainExecutor<Usage> chainExecutor;;

    /**
     * Finds NTS usages in NON_STM_RH status and send to RH Eligibility queue.
     */
    @Override
    public void executeInternal(JobExecutionContext context) {
        context.setResult(chainExecutor.execute(ChainProcessorTypeEnum.RH_ELIGIBILITY));
    }
}
