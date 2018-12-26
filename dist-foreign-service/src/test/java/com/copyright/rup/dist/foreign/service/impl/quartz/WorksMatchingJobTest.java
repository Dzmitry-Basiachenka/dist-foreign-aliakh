package com.copyright.rup.dist.foreign.service.impl.quartz;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.ChainProcessorTypeEnum;
import com.copyright.rup.dist.foreign.service.api.IChainExecutor;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

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

    private WorksMatchingJob job;
    private IChainExecutor<Usage> executor;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        executor = createMock(IChainExecutor.class);
        job = new WorksMatchingJob();
        Whitebox.setInternalState(job, executor);
    }

    @Test
    public void testExecuteInternal() {
        executor.execute(ChainProcessorTypeEnum.MATCHING);
        expectLastCall().once();
        replay(executor);
        job.executeInternal(null);
        verify(executor);
    }
}
