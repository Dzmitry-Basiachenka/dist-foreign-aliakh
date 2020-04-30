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
 * Verifies {@link RhEligibilityJob}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 01/14/2019
 *
 * @author Uladzislau Shalamitski
 */
public class RhEligibilityJobTest {

    private RhEligibilityJob rhEligibilityJob;
    private IChainExecutor<Usage> chainExecutor;
    private JobExecutionContext jobExecutionContext;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        chainExecutor = createMock(IChainExecutor.class);
        jobExecutionContext = createMock(JobExecutionContext.class);
        rhEligibilityJob = new RhEligibilityJob();
        Whitebox.setInternalState(rhEligibilityJob, "chainExecutor", chainExecutor);
        Whitebox.setInternalState(rhEligibilityJob, "useChunks", false);
    }

    @Test
    public void testExecuteInternal() {
        JobInfo jobInfo = new JobInfo(JobStatusEnum.FINISHED, "ProductFamily=FAS, Reason=There are no usages");
        expect(chainExecutor.execute(ChainProcessorTypeEnum.RH_ELIGIBILITY)).andReturn(jobInfo).once();
        jobExecutionContext.setResult(jobInfo);
        expectLastCall().once();
        replay(chainExecutor, jobExecutionContext);
        rhEligibilityJob.executeInternal(jobExecutionContext);
        verify(chainExecutor, jobExecutionContext);
    }
}
