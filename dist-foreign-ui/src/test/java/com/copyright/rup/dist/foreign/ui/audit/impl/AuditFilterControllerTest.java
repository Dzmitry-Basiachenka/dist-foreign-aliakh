package com.copyright.rup.dist.foreign.ui.audit.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link AuditFilterController}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/22/18
 *
 * @author Aliaksandr Radkevich
 */
public class AuditFilterControllerTest {

    private AuditFilterController controller;
    private IUsageBatchService usageBatchService;
    private IRightsholderService rightsholderService;

    @Before
    public void setUp() {
        controller = new AuditFilterController();
        usageBatchService = createMock(IUsageBatchService.class);
        rightsholderService = createMock(IRightsholderService.class);
        Whitebox.setInternalState(controller, "usageBatchService", usageBatchService);
        Whitebox.setInternalState(controller, "rightsholderService", rightsholderService);
    }

    @Test
    public void testGetRightsholders() {
        List<Rightsholder> rightsholders = Collections.emptyList();
        expect(rightsholderService.getFromUsages()).andReturn(rightsholders).once();
        replay(rightsholderService);
        assertSame(rightsholders, controller.getRightsholders());
        verify(rightsholderService);
    }

    @Test
    public void testGetUsageBatches() {
        List<UsageBatch> usageBatches = Collections.emptyList();
        expect(usageBatchService.getUsageBatches()).andReturn(usageBatches).once();
        replay(usageBatchService);
        assertSame(usageBatches, controller.getUsageBatches());
        verify(usageBatchService);
    }

    @Test
    public void testInstantiateWidget() {
        assertTrue(controller.instantiateWidget() instanceof AuditFilterWidget);
    }
}
