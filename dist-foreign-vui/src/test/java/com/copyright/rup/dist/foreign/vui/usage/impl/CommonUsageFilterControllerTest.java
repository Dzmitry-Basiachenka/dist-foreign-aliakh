package com.copyright.rup.dist.foreign.vui.usage.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.vui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.vui.usage.api.ICommonUsageFilterWidget;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.List;
import java.util.Set;

/**
 * Verifies {@link CommonUsageFilterController}.
 * <p>
 * Copyright (C) 2024 copyright.com
 * <p>
 * Date: 01/10/2024
 *
 * @author Aliaksandr Liakh
 */
public class CommonUsageFilterControllerTest {

    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private static final Long RH_ACCOUNT_NUMBER = 1000000001L;

    private CommonUsageFilterController controller;
    private IProductFamilyProvider productFamilyProvider;
    private IUsageBatchService usageBatchService;
    private IRightsholderService rightsholderService;

    @Before
    public void setUp() {
        productFamilyProvider = createMock(IProductFamilyProvider.class);
        usageBatchService = createMock(IUsageBatchService.class);
        rightsholderService = createMock(IRightsholderService.class);
        controller = new TestUsageFilterController();
        Whitebox.setInternalState(controller, productFamilyProvider);
        Whitebox.setInternalState(controller, usageBatchService);
        Whitebox.setInternalState(controller, rightsholderService);
    }

    @Test
    public void testGetSelectedProductFamily() {
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).once();
        replay(productFamilyProvider, usageBatchService, rightsholderService);
        assertSame(FAS_PRODUCT_FAMILY, controller.getSelectedProductFamily());
        verify(productFamilyProvider, usageBatchService, rightsholderService);
    }

    @Test
    public void testGetUsageBatches() {
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).once();
        List<UsageBatch> usageBatches = List.of(new UsageBatch());
        expect(usageBatchService.getUsageBatches(FAS_PRODUCT_FAMILY)).andReturn(usageBatches).once();
        replay(productFamilyProvider, usageBatchService, rightsholderService);
        assertSame(usageBatches, controller.getUsageBatches());
        verify(productFamilyProvider, usageBatchService, rightsholderService);
    }

    @Test
    public void testGetRightsholdersByAccountNumbers() {
        Set<Long> accountNumbers = Set.of(RH_ACCOUNT_NUMBER);
        List<Rightsholder> rightsholders = List.of(new Rightsholder());
        expect(rightsholderService.getRightsholdersByAccountNumbers(accountNumbers)).andReturn(rightsholders).once();
        replay(productFamilyProvider, usageBatchService, rightsholderService);
        assertSame(rightsholders, controller.getRightsholdersByAccountNumbers(accountNumbers));
        verify(productFamilyProvider, usageBatchService, rightsholderService);
    }

    private static class TestUsageFilterController extends CommonUsageFilterController {

        @Override
        protected ICommonUsageFilterWidget instantiateWidget() {
            return createMock(ICommonUsageFilterWidget.class);
        }
    }
}
