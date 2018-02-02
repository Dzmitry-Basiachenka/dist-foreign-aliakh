package com.copyright.rup.dist.foreign.service.impl.quartz;

import com.copyright.rup.dist.foreign.service.api.IUsageService;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Quartz job to to get rights for usages.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 01/16/2018
 *
 * @author Aliaksandr Liakh
 */
@DisallowConcurrentExecution
public class GetRightsQuartzJob extends QuartzJobBean {

    private IUsageService usageService;

    public void setUsageService(IUsageService usageService) {
        this.usageService = usageService;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) {
        usageService.updateRightsholders();
    }
}
