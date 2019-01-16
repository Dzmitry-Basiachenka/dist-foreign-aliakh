package com.copyright.rup.dist.foreign.service.impl.quartz;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import org.junit.Test;

/**
 * Verifies {@link RhEligibilityJob}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 01/14/2019
 *
 * @author Uladzislau Shalamitski
 */
public class RhEligibilityQuartzJobTest {

    @Test
    @SuppressWarnings("unchecked")
    public void testExecuteInternal() {
        RhEligibilityJob rhEligibilityJob = new RhEligibilityJob();
        IChainExecutor<Usage> executor = createMock(IChainExecutor.class);
        rhEligibilityJob.setExecutor(executor);
        executor.execute(ChainProcessorTypeEnum.RH_ELIGIBILITY);
        expectLastCall().once();
        replay(executor);
        rhEligibilityJob.executeInternal(null);
        verify(executor);
    }
}
