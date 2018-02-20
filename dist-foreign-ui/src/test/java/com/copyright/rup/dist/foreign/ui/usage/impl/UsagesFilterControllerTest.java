package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import com.google.common.collect.Lists;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link UsagesFilterController}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 1/20/17
 *
 * @author Aliaksandr Radkevich
 */
public class UsagesFilterControllerTest {

    private static final Integer FISCAL_YEAR = 2017;
    private UsagesFilterController controller;

    @Before
    public void setUp() {
        controller = new UsagesFilterController();
    }

    @Test
    public void testInstantiateWidget() {
        assertNotNull(controller.instantiateWidget());
    }

    @Test
    public void testGetFiscalYears() {
        IUsageBatchService usageBatchService = createMock(IUsageBatchService.class);
        Whitebox.setInternalState(controller, "usageBatchService", usageBatchService);
        expect(usageBatchService.getFiscalYears()).andReturn(Collections.singletonList(FISCAL_YEAR)).once();
        replay(usageBatchService);
        List<Integer> fiscalYears = controller.getFiscalYears();
        assertTrue(CollectionUtils.isNotEmpty(fiscalYears));
        assertEquals(1, fiscalYears.size());
        assertTrue(fiscalYears.contains(FISCAL_YEAR));
        verify(usageBatchService);
    }

    @Test
    public void testGetUsageBatchesForFilter() {
        IUsageBatchService usageBatchService = createMock(IUsageBatchService.class);
        Whitebox.setInternalState(controller, IUsageBatchService.class, usageBatchService);
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setName("name");
        expect(usageBatchService.getUsageBatchesForFilter()).andReturn(Lists.newArrayList(usageBatch)).once();
        replay(usageBatchService);
        List<UsageBatch> usageBatches = controller.getUsageBatchesNotIncludedIntoScenario();
        assertEquals(1, usageBatches.size());
        assertEquals(usageBatch.getName(), usageBatches.iterator().next().getName());
        verify(usageBatchService);
    }

    @Test
    public void testGetRros() {
        IRightsholderService rightsholderService = createMock(IRightsholderService.class);
        Whitebox.setInternalState(controller, IRightsholderService.class, rightsholderService);
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(12345678L);
        expect(rightsholderService.getRros()).andReturn(Lists.newArrayList(rightsholder)).once();
        replay(rightsholderService);
        List<Rightsholder> rightsholders = controller.getRros();
        assertEquals(1, rightsholders.size());
        assertEquals(rightsholder.getAccountNumber(), rightsholders.iterator().next().getAccountNumber());
        verify(rightsholderService);
    }

    @Test
    public void testGetProductFamilies() {
        IUsageService usageService = createMock(IUsageService.class);
        Whitebox.setInternalState(controller, IUsageService.class, usageService);
        List<String> expectedProductFamilies = Lists.newArrayList("FAS", "NTS");
        expect(usageService.getProductFamilies())
            .andReturn(expectedProductFamilies)
            .once();
        replay(usageService);
        List<String> productFamilies = controller.getProductFamilies();
        assertEquals(2, productFamilies.size());
        assertTrue(CollectionUtils.isEqualCollection(expectedProductFamilies, productFamilies));
        verify(usageService);
    }
}
