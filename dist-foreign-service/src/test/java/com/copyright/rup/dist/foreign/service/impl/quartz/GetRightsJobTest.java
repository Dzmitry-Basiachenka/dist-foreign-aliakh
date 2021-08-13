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
    private IChainExecutor<Usage> chainExecutor;
    private IChainExecutor<UdmUsage> udmChainExecutor;
    private JobExecutionContext jobExecutionContext;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        chainExecutor = createMock(IChainExecutor.class);
        udmChainExecutor = createMock(IChainExecutor.class);
        jobExecutionContext = createMock(JobExecutionContext.class);
        getRightsJob = new GetRightsJob();
        Whitebox.setInternalState(getRightsJob, "chainExecutor", chainExecutor);
        Whitebox.setInternalState(getRightsJob, "udmChainExecutor", udmChainExecutor);
    }

    @Test
    public void testExecuteInternal() {
        JobInfo usageJobInfo = new JobInfo(JobStatusEnum.SKIPPED, "ProductFamily=FAS, Reason=There are no usages");
        expect(chainExecutor.execute(ChainProcessorTypeEnum.RIGHTS)).andReturn(usageJobInfo).once();
        JobInfo udmJobInfo = new JobInfo(JobStatusEnum.FINISHED, "ProductFamily=ACL_UDM, UsagesCount=5");
        expect(udmChainExecutor.execute(ChainProcessorTypeEnum.RIGHTS)).andReturn(udmJobInfo).once();
        JobInfo result = new JobInfo(JobStatusEnum.FINISHED,
            "ProductFamily=FAS, Reason=There are no usages; ProductFamily=ACL_UDM, UsagesCount=5");
        jobExecutionContext.setResult(result);
        expectLastCall().once();
        replay(chainExecutor, udmChainExecutor, jobExecutionContext);
        getRightsJob.executeInternal(jobExecutionContext);
        verify(chainExecutor, udmChainExecutor, jobExecutionContext);
    }
}
