package com.copyright.rup.dist.foreign.ui.mock;

import com.copyright.rup.dist.foreign.service.impl.quartz.WorksMatchingJob;

import org.quartz.JobExecutionContext;

/**
 * Mocks {@link WorksMatchingJob}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 04/11/2018
 *
 * @author Uladzislau_Shalamitski
 */
public class WorksMatchingJobMock extends WorksMatchingJob {

    @Override
    public void executeInternal(JobExecutionContext context) {
        // empty method
    }
}
