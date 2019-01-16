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
 * Verifies {@link RhTaxJob}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 12/05/2018
 *
 * @author Pavel Liakh
 */
public class RhTaxQuartzJobTest {

    @Test
    @SuppressWarnings("unchecked")
    public void testExecuteInternal() {
        RhTaxJob rhTaxJob = new RhTaxJob();
        IChainExecutor<Usage> executor = createMock(IChainExecutor.class);
        rhTaxJob.setExecutor(executor);
        executor.execute(ChainProcessorTypeEnum.RH_TAX);
        expectLastCall().once();
        replay(executor);
        rhTaxJob.executeInternal(null);
        verify(executor);
    }
}
