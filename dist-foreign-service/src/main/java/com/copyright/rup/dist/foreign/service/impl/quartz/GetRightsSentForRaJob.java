package com.copyright.rup.dist.foreign.service.impl.quartz;

import com.copyright.rup.dist.foreign.service.api.IRightsService;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * Quartz job to update rights for SENT_FOR_RA usages.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 01/15/2019
 *
 * @author Uladzislau Shalamitski
 */
@DisallowConcurrentExecution
@Component
public class GetRightsSentForRaJob extends QuartzJobBean {

    @Autowired
    private IRightsService rightsService;

    @Override
    public void executeInternal(JobExecutionContext context) {
        context.setResult(rightsService.updateRightsSentForRaUsages());
    }
}
