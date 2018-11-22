package com.copyright.rup.dist.foreign.service.impl.quartz;

import com.copyright.rup.dist.foreign.service.api.IRightsService;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
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
    private IRightsService rightsService;

    @Override
    public void executeInternal(JobExecutionContext context) {
        rightsService.updateRights();
    }
}
