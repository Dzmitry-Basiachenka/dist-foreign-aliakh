package com.copyright.rup.dist.foreign.ui.audit.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isNull;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import com.google.common.collect.Lists;

import org.apache.commons.collections4.CollectionUtils;
import org.easymock.Capture;
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
    private IUsageService usageService;

    @Before
    public void setUp() {
        controller = new AuditFilterController();
        usageBatchService = createMock(IUsageBatchService.class);
        rightsholderService = createMock(IRightsholderService.class);
        usageService = createMock(IUsageService.class);
        Whitebox.setInternalState(controller, "usageService", usageService);
        Whitebox.setInternalState(controller, "usageBatchService", usageBatchService);
        Whitebox.setInternalState(controller, "rightsholderService", rightsholderService);
    }

    @Test
    public void testGetRightsholders() {
        List<Rightsholder> rightsholders = Collections.emptyList();
        Capture<Pageable> pageableCapture = new Capture<>();
        expect(rightsholderService.getFromUsages(eq("search"), capture(pageableCapture), isNull()))
            .andReturn(rightsholders).once();
        replay(rightsholderService);
        assertSame(rightsholders, controller.loadBeans("search", 0, 10, null));
        assertEquals(10, pageableCapture.getValue().getLimit());
        assertEquals(0, pageableCapture.getValue().getOffset());
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

    @Test
    public void testGetProductFamilies() {
        List<String> expectedProductFamilies = Lists.newArrayList("FAS", "NTS");
        expect(usageService.getProductFamiliesForAudit())
            .andReturn(expectedProductFamilies)
            .once();
        replay(usageService);
        List<String> productFamilies = controller.getProductFamilies();
        assertTrue(CollectionUtils.isNotEmpty(productFamilies));
        assertTrue(CollectionUtils.isEqualCollection(expectedProductFamilies, productFamilies));
        verify(usageService);
    }
}
