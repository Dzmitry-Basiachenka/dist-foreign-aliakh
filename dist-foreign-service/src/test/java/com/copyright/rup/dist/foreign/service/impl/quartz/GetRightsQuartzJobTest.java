package com.copyright.rup.dist.foreign.service.impl.quartz;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.foreign.service.api.IUsageService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

/**
 * Verifies {@link GetRightsQuartzJob}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 01/16/2018
 *
 * @author Aliaksandr Liakh
 */
public class GetRightsQuartzJobTest {

    private IUsageService usageService;
    private GetRightsQuartzJob getRightsQuartzJob;

    @Before
    public void setUp() {
        usageService = createMock(IUsageService.class);
        getRightsQuartzJob = new GetRightsQuartzJob();
        Whitebox.setInternalState(getRightsQuartzJob, "usageService", usageService);
    }

    @Test
    public void testExecuteInternal() throws Exception {
        usageService.updateRightsholders();
        expectLastCall();
        replay(usageService);
        getRightsQuartzJob.executeInternal(null);
        verify(usageService);
    }
}
