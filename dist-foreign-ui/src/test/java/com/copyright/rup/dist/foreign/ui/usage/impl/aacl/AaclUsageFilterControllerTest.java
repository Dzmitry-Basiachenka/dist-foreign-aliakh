package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.ui.main.api.IProductFamilyProvider;

import com.google.common.collect.Lists;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link AaclUsageFilterController}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 12/23/2019
 *
 * @author Aliaksandr Liakh
 */
public class AaclUsageFilterControllerTest {

    private static final String AACL_PRODUCT_FAMILY = "AACL";

    private AaclUsageFilterController controller;
    private IProductFamilyProvider productFamilyProvider;
    private IUsageBatchService usageBatchService;
    private IUsageService usageService;

    @Before
    public void setUp() {
        productFamilyProvider = createMock(IProductFamilyProvider.class);
        usageBatchService = createMock(IUsageBatchService.class);
        usageService = createMock(IUsageService.class);
        controller = new AaclUsageFilterController();
        Whitebox.setInternalState(controller, productFamilyProvider);
        Whitebox.setInternalState(controller, usageBatchService);
        Whitebox.setInternalState(controller, usageService);
    }

    @Test
    public void testInstantiateWidget() {
        assertNotNull(controller.instantiateWidget());
    }

    @Test
    public void testGetUsageBatchesForFilter() {
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(AACL_PRODUCT_FAMILY).once();
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setName("name");
        expect(usageBatchService.getUsageBatches(AACL_PRODUCT_FAMILY)).andReturn(Lists.newArrayList(usageBatch)).once();
        replay(usageBatchService, productFamilyProvider);
        List<UsageBatch> usageBatches = controller.getUsageBatches();
        assertEquals(1, CollectionUtils.size(usageBatches));
        assertEquals(usageBatch.getName(), usageBatches.iterator().next().getName());
        verify(usageBatchService, productFamilyProvider);
    }

    @Test
    public void testGetAaclUsagePeriods() {
        List<Integer> usagePeriods = Collections.singletonList(2020);
        expect(usageService.getAaclUsagePeriods()).andReturn(usagePeriods).once();
        replay(usageService);
        assertEquals(usagePeriods, controller.getAaclUsagePeriods());
        verify(usageService);
    }
}
