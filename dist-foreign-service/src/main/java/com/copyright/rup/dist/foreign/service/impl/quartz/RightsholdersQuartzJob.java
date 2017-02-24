package com.copyright.rup.dist.foreign.service.impl.quartz;

import com.copyright.rup.dist.foreign.service.api.IRightsholderService;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Quartz job to make call to PRM service to get information about rightsholders and store in database.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/24/2017
 *
 * @author Mikalai Bezmen
 */
@DisallowConcurrentExecution
public class RightsholdersQuartzJob extends QuartzJobBean {

    private IRightsholderService rightsholderService;

    /**
     * Sets rightsholder service.
     *
     * @param rightsholderService {@link IRightsholderService} instance
     */
    public void setRightsholderService(IRightsholderService rightsholderService) {
        this.rightsholderService = rightsholderService;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        rightsholderService.updateRightsholdersInformation();
    }
}
