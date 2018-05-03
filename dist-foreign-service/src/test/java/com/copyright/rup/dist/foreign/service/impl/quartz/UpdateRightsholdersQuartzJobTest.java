package com.copyright.rup.dist.foreign.service.impl.quartz;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.foreign.service.api.IRightsholderService;

import org.junit.Test;
import org.powermock.reflect.Whitebox;

/**
 * Verifies {@link UpdateRightsholdersJob}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 04/24/2018
 *
 * @author Aliaksandr Liakh
 */
public class UpdateRightsholdersQuartzJobTest {

    @Test
    public void testExecuteInternal() {
        IRightsholderService rightsholderService = createMock(IRightsholderService.class);
        UpdateRightsholdersJob job = new UpdateRightsholdersJob();
        Whitebox.setInternalState(job, rightsholderService);
        rightsholderService.updateRightsholders();
        expectLastCall().once();
        replay(rightsholderService);
        job.executeInternal(null);
        verify(rightsholderService);
    }
}
