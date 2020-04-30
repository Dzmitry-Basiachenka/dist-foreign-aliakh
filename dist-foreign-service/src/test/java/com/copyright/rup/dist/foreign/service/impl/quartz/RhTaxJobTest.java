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
 * Verifies {@link RhTaxJob}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 12/05/2018
 *
 * @author Pavel Liakh
 */
public class RhTaxJobTest {

    private RhTaxJob rhTaxJob;
    private IChainExecutor<Usage> chainExecutor;
    private JobExecutionContext jobExecutionContext;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        chainExecutor = createMock(IChainExecutor.class);
        jobExecutionContext = createMock(JobExecutionContext.class);
        rhTaxJob = new RhTaxJob();
        Whitebox.setInternalState(rhTaxJob, "chainExecutor", chainExecutor);
        Whitebox.setInternalState(rhTaxJob, "useChunks", false);
    }

    @Test
    public void testExecuteInternal() {
        JobInfo jobInfo = new JobInfo(JobStatusEnum.FINISHED, "ProductFamily=FAS, Reason=There are no usages");
        expect(chainExecutor.execute(ChainProcessorTypeEnum.RH_TAX)).andReturn(jobInfo).once();
        jobExecutionContext.setResult(jobInfo);
        expectLastCall().once();
        replay(chainExecutor, jobExecutionContext);
        rhTaxJob.executeInternal(jobExecutionContext);
        verify(chainExecutor, jobExecutionContext);
    }
}
