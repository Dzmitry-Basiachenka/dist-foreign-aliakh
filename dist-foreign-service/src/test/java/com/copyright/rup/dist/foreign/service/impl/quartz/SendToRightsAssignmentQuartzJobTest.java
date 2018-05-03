package com.copyright.rup.dist.foreign.service.impl.quartz;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.foreign.service.api.IRightsService;

import org.junit.Test;
import org.powermock.reflect.Whitebox;

/**
 * Verifies {@link SendToRightsAssignmentJob}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 04/24/2018
 *
 * @author Aliaksandr Liakh
 */
public class SendToRightsAssignmentQuartzJobTest {

    @Test
    public void testExecuteInternal() {
        IRightsService rightsService = createMock(IRightsService.class);
        SendToRightsAssignmentJob job = new SendToRightsAssignmentJob();
        Whitebox.setInternalState(job, rightsService);
        rightsService.sendForRightsAssignment();
        expectLastCall().once();
        replay(rightsService);
        job.executeInternal(null);
        verify(rightsService);
    }
}
