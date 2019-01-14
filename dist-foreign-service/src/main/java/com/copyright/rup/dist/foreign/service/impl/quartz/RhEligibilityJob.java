package com.copyright.rup.dist.foreign.service.impl.quartz;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.ChainProcessorTypeEnum;
import com.copyright.rup.dist.foreign.service.api.IChainExecutor;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * Quartz job to send NTS usages in US_TAX_COUNTRY status to RH Eligibility queue for further processing.
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
    private IChainExecutor<Usage> executor;

    /**
     * Finds NTS usages in UA_TAX_COUNTRY status and send to RH Eligibility queue.
     */
    @Override
    public void executeInternal(JobExecutionContext context) {
        executor.execute(ChainProcessorTypeEnum.RH_ELIGIBILITY);
    }

    void setExecutor(IChainExecutor<Usage> executor) {
        this.executor = executor;
    }
}
