package com.copyright.rup.dist.foreign.service.impl.quartz;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.foreign.domain.job.JobInfo;
import com.copyright.rup.dist.foreign.domain.job.JobStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IRightsService;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import org.quartz.JobExecutionContext;

/**
 * Verifies {@link GetRightsSentForRaJob}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 01/16/2019
 *
 * @author Uladzislau Shalamitski
 */
public class GetRightsSentForRaJobTest {

    private IRightsService rightsService;
    private JobExecutionContext jobExecutionContext;
    private GetRightsSentForRaJob job;

    @Before
    public void setUp() {
        rightsService = createMock(IRightsService.class);
        jobExecutionContext = createMock(JobExecutionContext.class);
        job = new GetRightsSentForRaJob();
        Whitebox.setInternalState(job, "rightsService", rightsService);
    }

    @Test
    public void testExecuteInternal() {
        JobInfo jobInfo = new JobInfo(JobStatusEnum.FINISHED, "ProductFamily=FAS, UsagesCount=100");
        expect(rightsService.updateRightsSentForRaUsages()).andReturn(jobInfo).once();
        jobExecutionContext.setResult(jobInfo);
        expectLastCall().once();
        replay(rightsService, jobExecutionContext);
        job.executeInternal(jobExecutionContext);
        verify(rightsService, jobExecutionContext);
    }
}
