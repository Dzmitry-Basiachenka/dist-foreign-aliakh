package com.copyright.rup.dist.foreign.service.impl.quartz;

import com.copyright.rup.dist.foreign.service.api.IUsageService;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Quartz job to make call to RMS to send usages for righsts assignment.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/30/18
 *
 * @author Darya Baraukova
 */
@DisallowConcurrentExecution
public class RightsAssignmentQuartzJob extends QuartzJobBean {

    private IUsageService usageService;

    /**
     * Sets usage service.
     *
     * @param usageService {@link IUsageService} instance
     */
    public void setUsageService(IUsageService usageService) {
        this.usageService = usageService;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) {
        usageService.sendForRightsAssignment();
    }
}
