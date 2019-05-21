package com.copyright.rup.dist.foreign.service.impl.quartz;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.foreign.domain.job.JobInfo;
import com.copyright.rup.dist.foreign.domain.job.JobStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IRightsService;

import org.junit.Test;
import org.powermock.reflect.Whitebox;
import org.quartz.JobExecutionContext;

/**
 * Verifies {@link SendToRightsAssignmentJob}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 04/24/2018
 *
 * @author Aliaksandr Liakh
 */
public class SendToRightsAssignmentJobTest {

    @Test
    public void testExecuteInternal() {
        IRightsService rightsService = createMock(IRightsService.class);
        JobExecutionContext jobExecutionContext = createMock(JobExecutionContext.class);
        SendToRightsAssignmentJob job = new SendToRightsAssignmentJob();
        Whitebox.setInternalState(job, rightsService);
        JobInfo jobInfo = new JobInfo(JobStatusEnum.FINISHED, "UsagesCount=2");
        expect(rightsService.sendForRightsAssignment()).andReturn(jobInfo).once();
        jobExecutionContext.setResult(jobInfo);
        expectLastCall().once();
        replay(rightsService, jobExecutionContext);
        job.executeInternal(jobExecutionContext);
        verify(rightsService, jobExecutionContext);
    }
}
