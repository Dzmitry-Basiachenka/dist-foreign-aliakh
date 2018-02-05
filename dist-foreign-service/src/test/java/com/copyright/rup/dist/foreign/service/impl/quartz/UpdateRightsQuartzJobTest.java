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
 * Verifies {@link UpdateRightsQuartzJob}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 01/16/2018
 *
 * @author Aliaksandr Liakh
 */
public class UpdateRightsQuartzJobTest {

    private IUsageService usageService;
    private UpdateRightsQuartzJob updateRightsQuartzJob;

    @Before
    public void setUp() {
        usageService = createMock(IUsageService.class);
        updateRightsQuartzJob = new UpdateRightsQuartzJob();
        Whitebox.setInternalState(updateRightsQuartzJob, "usageService", usageService);
    }

    @Test
    public void testExecuteInternal() throws Exception {
        usageService.updateRightsholders();
        expectLastCall();
        replay(usageService);
        updateRightsQuartzJob.executeInternal(null);
        verify(usageService);
    }
}
