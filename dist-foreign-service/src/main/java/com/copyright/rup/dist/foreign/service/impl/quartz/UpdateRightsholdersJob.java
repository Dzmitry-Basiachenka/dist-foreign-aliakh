package com.copyright.rup.dist.foreign.service.impl.quartz;

import com.copyright.rup.dist.foreign.service.api.IRightsholderService;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * Quartz job to update rightsholders from PRM.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 04/24/2018
 *
 * @author Aliaksandr Liakh
 */
@DisallowConcurrentExecution
@Component
public class UpdateRightsholdersJob extends QuartzJobBean {

    @Autowired
    private IRightsholderService rightsholderService;

    @Override
    public void executeInternal(JobExecutionContext context) {
        rightsholderService.updateRightsholders();
    }
}
