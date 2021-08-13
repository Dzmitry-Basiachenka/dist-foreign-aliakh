package com.copyright.rup.dist.foreign.service.impl.quartz;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.common.domain.job.JobStatusEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import org.quartz.JobExecutionContext;

/**
 * Verifies {@link WorksMatchingJob}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/20/17
 *
 * @author Darya Baraukova
 */
public class WorksMatchingJobTest {

    private WorksMatchingJob worksMatchingJob;
    private IChainExecutor<Usage> usageChainExecutor;
    private IChainExecutor<UdmUsage> udmChainExecutor;
    private JobExecutionContext jobExecutionContext;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        usageChainExecutor = createMock(IChainExecutor.class);
        udmChainExecutor = createMock(IChainExecutor.class);
        jobExecutionContext = createMock(JobExecutionContext.class);
        worksMatchingJob = new WorksMatchingJob();
        Whitebox.setInternalState(worksMatchingJob, "usageChainExecutor", usageChainExecutor);
        Whitebox.setInternalState(worksMatchingJob, "udmChainExecutor", udmChainExecutor);
    }

    @Test
    public void testExecuteInternal() {
        JobInfo usageJobInfo = new JobInfo(JobStatusEnum.SKIPPED, "ProductFamily=FAS, Reason=There are no usages");
        expect(usageChainExecutor.execute(ChainProcessorTypeEnum.MATCHING)).andReturn(usageJobInfo).once();
        JobInfo udmJobInfo = new JobInfo(JobStatusEnum.FINISHED, "ProductFamily=ACL_UDM, UsagesCount=5");
        expect(udmChainExecutor.execute(ChainProcessorTypeEnum.MATCHING)).andReturn(udmJobInfo).once();
        JobInfo result = new JobInfo(JobStatusEnum.FINISHED,
            "ProductFamily=FAS, Reason=There are no usages; ProductFamily=ACL_UDM, UsagesCount=5");
        jobExecutionContext.setResult(result);
        expectLastCall().once();
        replay(usageChainExecutor, udmChainExecutor, jobExecutionContext);
        worksMatchingJob.executeInternal(jobExecutionContext);
        verify(usageChainExecutor, udmChainExecutor, jobExecutionContext);
    }
}
