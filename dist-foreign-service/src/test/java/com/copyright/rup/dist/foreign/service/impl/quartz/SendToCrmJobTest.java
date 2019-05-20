package com.copyright.rup.dist.foreign.service.impl.quartz;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.foreign.domain.job.JobInfo;
import com.copyright.rup.dist.foreign.domain.job.JobStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import org.junit.Test;
import org.powermock.reflect.Whitebox;
import org.quartz.JobExecutionContext;

/**
 * Verifies {@link SendToCrmJob}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 04/24/2018
 *
 * @author Aliaksandr Liakh
 */
public class SendToCrmJobTest {

    @Test
    public void testExecuteInternal() {
        IUsageService usageService = createMock(IUsageService.class);
        JobExecutionContext jobExecutionContext = createMock(JobExecutionContext.class);
        SendToCrmJob job = new SendToCrmJob();
        Whitebox.setInternalState(job, usageService);
        JobInfo jobInfo = new JobInfo(JobStatusEnum.FINISHED,
            "PaidUsagesCount=5, ArchivedUsagesCount=5, NotReportedUsagesCount=0, ArchivedScenariosCount=1");
        expect(usageService.sendToCrm()).andReturn(jobInfo).once();
        jobExecutionContext.setResult(jobInfo);
        expectLastCall().once();
        replay(usageService, jobExecutionContext);
        job.executeInternal(jobExecutionContext);
        verify(usageService, jobExecutionContext);
    }
}
