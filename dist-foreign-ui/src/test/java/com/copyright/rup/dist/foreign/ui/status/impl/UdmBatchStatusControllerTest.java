package com.copyright.rup.dist.foreign.ui.status.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.foreign.domain.UsageBatchStatus;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchStatusService;
import com.copyright.rup.dist.foreign.ui.status.api.IUdmBatchStatusWidget;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.List;

/**
 * Verifies {@link UdmBatchStatusController}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 02/08/2023
 *
 * @author Dzmitry Basiachenka
 */
public class UdmBatchStatusControllerTest {

    private UdmBatchStatusController controller;
    private IUsageBatchStatusService batchStatusService;

    @Before
    public void setUp() {
        controller = new UdmBatchStatusController();
        batchStatusService = createMock(IUsageBatchStatusService.class);
        Whitebox.setInternalState(controller, batchStatusService);
    }

    @Test
    public void testGetBatchStatuses() {
        List<UsageBatchStatus> usageBatchStatuses = List.of(new UsageBatchStatus());
        expect(batchStatusService.getUsageBatchStatusesUdm()).andReturn(usageBatchStatuses).once();
        replay(batchStatusService);
        assertSame(usageBatchStatuses, controller.getBatchStatuses());
        verify(batchStatusService);
    }

    @Test
    public void testInstantiateWidget() {
        IUdmBatchStatusWidget udmBatchStatusWidget = controller.instantiateWidget();
        assertNotNull(udmBatchStatusWidget);
        assertEquals(UdmBatchStatusWidget.class, udmBatchStatusWidget.getClass());
    }
}
