package com.copyright.rup.dist.foreign.vui.status.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.foreign.domain.UsageBatchStatus;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchStatusService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.List;

/**
 * Verifies {@link NtsBatchStatusController}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 02/12/2021
 *
 * @author Darya Baraukova
 */
public class NtsBatchStatusControllerTest {

    private NtsBatchStatusController controller;
    private IUsageBatchStatusService usageBatchStatusService;

    @Before
    public void setUp() {
        usageBatchStatusService = createMock(IUsageBatchStatusService.class);
        controller = new NtsBatchStatusController();
        Whitebox.setInternalState(controller, usageBatchStatusService);
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(controller.instantiateWidget(), instanceOf(NtsBatchStatusWidget.class));
    }

    @Test
    public void testGetBatchStatuses() {
        var batchStatuses = List.of(new UsageBatchStatus());
        expect(usageBatchStatusService.getUsageBatchStatusesNts()).andReturn(batchStatuses).once();
        replay(usageBatchStatusService);
        assertSame(batchStatuses, controller.getBatchStatuses());
        verify(usageBatchStatusService);
    }
}
