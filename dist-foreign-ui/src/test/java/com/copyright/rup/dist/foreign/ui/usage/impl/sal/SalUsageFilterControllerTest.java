package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.sal.ISalUsageService;
import com.copyright.rup.dist.foreign.ui.main.api.IProductFamilyProvider;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.List;

/**
 * Verifies {@link SalUsageFilterControllerTest}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 07/28/2020
 *
 * @author Uladzislau Shalamitski
 */
public class SalUsageFilterControllerTest {

    private static final String SAL_PRODUCT_FAMILY = "SAL";

    private SalUsageFilterController controller;
    private ISalUsageService salUsageService;
    private IProductFamilyProvider productFamilyProvider;
    private IUsageBatchService usageBatchService;

    @Before
    public void setUp() {
        salUsageService = createMock(ISalUsageService.class);
        productFamilyProvider = createMock(IProductFamilyProvider.class);
        usageBatchService = createMock(IUsageBatchService.class);
        controller = new SalUsageFilterController();
        Whitebox.setInternalState(controller, salUsageService);
        Whitebox.setInternalState(controller, productFamilyProvider);
        Whitebox.setInternalState(controller, usageBatchService);
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(controller.instantiateWidget(), instanceOf(SalUsageFilterWidget.class));
    }

    @Test
    public void testGetUsageBatchesForFilter() {
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(SAL_PRODUCT_FAMILY).once();
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setName("name");
        expect(usageBatchService.getUsageBatches(SAL_PRODUCT_FAMILY)).andReturn(List.of(usageBatch)).once();
        replay(usageBatchService, productFamilyProvider);
        List<UsageBatch> usageBatches = controller.getUsageBatches();
        assertEquals(1, CollectionUtils.size(usageBatches));
        assertEquals(usageBatch.getName(), usageBatches.iterator().next().getName());
        verify(usageBatchService, productFamilyProvider);
    }

    @Test
    public void testGetRightsholders() {
        List<Rightsholder> rightsholders = List.of(new Rightsholder());
        expect(salUsageService.getRightsholders()).andReturn(rightsholders).once();
        replay(salUsageService);
        assertSame(rightsholders, controller.getRightsholders());
        verify(salUsageService);
    }
}
