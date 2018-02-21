package com.copyright.rup.dist.foreign.service.impl.quartz;

import com.copyright.rup.dist.foreign.service.api.IUsageService;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Quartz job to get works from PI and update {@link com.copyright.rup.dist.foreign.domain.Usage}s.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 02/09/2018
 *
 * @author Aliaksandr Radkevich
 */
@DisallowConcurrentExecution
public class WorksMatchingQuartzJob extends QuartzJobBean {

    private IUsageService usageService;

    public void setUsageService(IUsageService usageService) {
        this.usageService = usageService;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        usageService.findWorksAndUpdateStatuses();
    }
}
