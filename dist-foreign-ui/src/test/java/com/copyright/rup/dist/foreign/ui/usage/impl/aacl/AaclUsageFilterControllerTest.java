package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclUsageService;
import com.copyright.rup.dist.foreign.ui.main.api.IProductFamilyProvider;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

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
    private IAaclUsageService aaclUsageService;

    @Before
    public void setUp() {
        productFamilyProvider = createMock(IProductFamilyProvider.class);
        usageBatchService = createMock(IUsageBatchService.class);
        aaclUsageService = createMock(IAaclUsageService.class);
        controller = new AaclUsageFilterController();
        Whitebox.setInternalState(controller, productFamilyProvider);
        Whitebox.setInternalState(controller, usageBatchService);
        Whitebox.setInternalState(controller, aaclUsageService);
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(controller.instantiateWidget(), instanceOf(AaclUsageFilterWidget.class));
    }

    @Test
    public void testGetUsageBatchesForFilter() {
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(AACL_PRODUCT_FAMILY).once();
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setName("name");
        expect(usageBatchService.getUsageBatches(AACL_PRODUCT_FAMILY)).andReturn(List.of(usageBatch)).once();
        replay(usageBatchService, productFamilyProvider);
        List<UsageBatch> usageBatches = controller.getUsageBatches();
        assertEquals(1, CollectionUtils.size(usageBatches));
        assertEquals(usageBatch.getName(), usageBatches.iterator().next().getName());
        verify(usageBatchService, productFamilyProvider);
    }

    @Test
    public void testGetUsagePeriods() {
        List<Integer> usagePeriods = List.of(2020);
        expect(aaclUsageService.getUsagePeriods()).andReturn(usagePeriods).once();
        replay(aaclUsageService);
        assertEquals(usagePeriods, controller.getUsagePeriods());
        verify(aaclUsageService);
    }
}
