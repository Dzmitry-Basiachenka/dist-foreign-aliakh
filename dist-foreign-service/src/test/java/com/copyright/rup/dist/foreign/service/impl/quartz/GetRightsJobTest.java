package com.copyright.rup.dist.foreign.service.impl.quartz;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.common.domain.job.JobStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import org.quartz.JobExecutionContext;

/**
 * Verifies {@link GetRightsJob}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 04/24/2018
 *
 * @author Aliaksandr Liakh
 */
public class GetRightsJobTest {

    private GetRightsJob getRightsJob;
    private IChainExecutor<Usage> executor;
    private JobExecutionContext jobExecutionContext;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        executor = createMock(IChainExecutor.class);
        jobExecutionContext = createMock(JobExecutionContext.class);
        getRightsJob = new GetRightsJob();
        Whitebox.setInternalState(getRightsJob, executor);
    }

    @Test
    public void testExecuteInternal() {
        JobInfo jobInfo = new JobInfo(JobStatusEnum.FINISHED, "ProductFamily=FAS, Reason=There are no usages");
        expect(executor.execute(ChainProcessorTypeEnum.RIGHTS)).andReturn(jobInfo).once();
        jobExecutionContext.setResult(jobInfo);
        expectLastCall().once();
        replay(executor, jobExecutionContext);
        getRightsJob.executeInternal(jobExecutionContext);
        verify(executor, jobExecutionContext);
    }
}
