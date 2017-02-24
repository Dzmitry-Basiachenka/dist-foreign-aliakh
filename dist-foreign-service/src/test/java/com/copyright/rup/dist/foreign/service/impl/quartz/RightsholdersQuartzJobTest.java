package com.copyright.rup.dist.foreign.service.impl.quartz;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.foreign.service.api.IRightsholderService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

/**
 * Verifies {@link RightsholdersQuartzJob}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/24/2017
 *
 * @author Mikalai Bezmen
 */
public class RightsholdersQuartzJobTest {

    private IRightsholderService rightsholderService;
    private RightsholdersQuartzJob rightsholdersQuartzJob;

    @Before
    public void setUp() {
        rightsholderService = createMock(IRightsholderService.class);
        rightsholdersQuartzJob = new RightsholdersQuartzJob();
        Whitebox.setInternalState(rightsholdersQuartzJob, "rightsholderService", rightsholderService);
    }

    @Test
    public void testExecuteInternal() throws Exception {
        expect(rightsholderService.updateRightsholdersInformation()).andReturn(1).once();
        replay(rightsholderService);
        rightsholdersQuartzJob.executeInternal(null);
        verify(rightsholderService);
    }
}
