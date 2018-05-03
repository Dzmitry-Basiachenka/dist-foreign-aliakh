package com.copyright.rup.dist.foreign.service.impl.quartz;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.foreign.service.api.IUsageService;

import org.junit.Test;
import org.powermock.reflect.Whitebox;

/**
 * Verifies {@link SendToCrmJob}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 04/24/2018
 *
 * @author Aliaksandr Liakh
 */
public class SendToCrmQuartzJobTest {

    @Test
    public void testExecuteInternal() {
        IUsageService usageService = createMock(IUsageService.class);
        SendToCrmJob job = new SendToCrmJob();
        Whitebox.setInternalState(job, usageService);
        usageService.sendToCrm();
        expectLastCall().once();
        replay(usageService);
        job.executeInternal(null);
        verify(usageService);
    }
}
