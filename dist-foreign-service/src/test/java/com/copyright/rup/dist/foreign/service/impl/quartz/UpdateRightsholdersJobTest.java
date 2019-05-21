package com.copyright.rup.dist.foreign.service.impl.quartz;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.common.domain.job.JobStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;

import org.junit.Test;
import org.powermock.reflect.Whitebox;
import org.quartz.JobExecutionContext;

/**
 * Verifies {@link UpdateRightsholdersJob}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 04/24/2018
 *
 * @author Aliaksandr Liakh
 */
public class UpdateRightsholdersJobTest {

    @Test
    public void testExecuteInternal() {
        IRightsholderService rightsholderService = createMock(IRightsholderService.class);
        JobExecutionContext jobExecutionContext = createMock(JobExecutionContext.class);
        UpdateRightsholdersJob job = new UpdateRightsholdersJob();
        Whitebox.setInternalState(job, rightsholderService);
        JobInfo jobInfo = new JobInfo(JobStatusEnum.FINISHED, "RHsCount=3, UpdatedCount=3");
        expect(rightsholderService.updateRightsholders()).andReturn(jobInfo).once();
        jobExecutionContext.setResult(jobInfo);
        expectLastCall().once();
        replay(rightsholderService, jobExecutionContext);
        job.executeInternal(jobExecutionContext);
        verify(rightsholderService, jobExecutionContext);
    }
}
