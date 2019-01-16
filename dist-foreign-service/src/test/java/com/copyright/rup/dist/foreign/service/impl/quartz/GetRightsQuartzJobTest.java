package com.copyright.rup.dist.foreign.service.impl.quartz;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import org.junit.Test;
import org.powermock.reflect.Whitebox;

/**
 * Verifies {@link GetRightsJob}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 04/24/2018
 *
 * @author Aliaksandr Liakh
 */
public class GetRightsQuartzJobTest {

    @Test
    @SuppressWarnings("unchecked")
    public void testExecuteInternal() {
        IChainExecutor<Usage> executor = createMock(IChainExecutor.class);
        GetRightsJob job = new GetRightsJob();
        Whitebox.setInternalState(job, executor);
        executor.execute(ChainProcessorTypeEnum.RIGHTS);
        expectLastCall().once();
        replay(executor);
        job.executeInternal(null);
        verify(executor);
    }
}
