package com.copyright.rup.dist.foreign.service.impl.quartz;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.foreign.service.api.IRightsService;
import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link GetRightsSentForRaJob}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 01/16/2019
 *
 * @author Uladzislau Shalamitski
 */
public class GetRightsSentForRaJobTest {

    private IRightsService rightsService;
    private GetRightsSentForRaJob job;

    @Before
    public void setUp() {
        rightsService = createMock(IRightsService.class);
        job = new GetRightsSentForRaJob();
        job.setRightsService(rightsService);
    }

    @Test
    public void testExecuteInternal() {
        rightsService.updateRightsSentForRaUsages();
        expectLastCall().once();
        replay(rightsService);
        job.executeInternal(null);
        verify(rightsService);
    }
}
