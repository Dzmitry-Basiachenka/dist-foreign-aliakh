package com.copyright.rup.dist.foreign.service.impl.quartz;

import com.copyright.rup.dist.foreign.service.api.IUsageService;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * Quartz job to send paid usages to CRM.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 04/24/2018
 *
 * @author Aliaksandr Liakh
 */
@DisallowConcurrentExecution
@Component
public class SendToCrmJob extends QuartzJobBean {

    @Autowired
    private IUsageService usageService;

    @Override
    public void executeInternal(JobExecutionContext context) {
        context.setResult(usageService.sendToCrm());
    }
}
