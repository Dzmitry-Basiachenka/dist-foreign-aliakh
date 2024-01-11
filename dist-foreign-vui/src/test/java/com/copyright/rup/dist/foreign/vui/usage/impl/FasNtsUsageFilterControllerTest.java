package com.copyright.rup.dist.foreign.vui.usage.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.vui.main.api.IProductFamilyProvider;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.List;

/**
 * Verifies {@link FasNtsUsageFilterController}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 12/12/19
 *
 * @author Uladzislau Shalamitski
 */
public class FasNtsUsageFilterControllerTest {

    private static final String FAS_PRODUCT_FAMILY = "FAS";

    private FasNtsUsageFilterController controller;
    private IProductFamilyProvider productFamilyProvider;
    private IUsageBatchService usageBatchService;
    private IRightsholderService rightsholderService;

    @Before
    public void setUp() {
        productFamilyProvider = createMock(IProductFamilyProvider.class);
        rightsholderService = createMock(IRightsholderService.class);
        usageBatchService = createMock(IUsageBatchService.class);
        controller = new FasNtsUsageFilterController();
        Whitebox.setInternalState(controller, productFamilyProvider);
        Whitebox.setInternalState(controller, usageBatchService);
        Whitebox.setInternalState(controller, rightsholderService);
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(controller.instantiateWidget(), instanceOf(FasNtsUsageFilterWidget.class));
    }

    @Test
    public void testGetFiscalYears() {
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).once();
        List<Integer> fiscalYears = List.of(2024);
        expect(usageBatchService.getFiscalYears(FAS_PRODUCT_FAMILY)).andReturn(fiscalYears).once();
        replay(productFamilyProvider, usageBatchService, rightsholderService);
        assertSame(fiscalYears, controller.getFiscalYears());
        verify(productFamilyProvider, usageBatchService, rightsholderService);
    }

    @Test
    public void testGetUsageBatchesForFilter() {
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).once();
        List<UsageBatch> usageBatches = List.of(new UsageBatch());
        expect(usageBatchService.getUsageBatches(FAS_PRODUCT_FAMILY)).andReturn(usageBatches).once();
        replay(productFamilyProvider, usageBatchService, rightsholderService);
        assertSame(usageBatches, controller.getUsageBatches());
        verify(productFamilyProvider, usageBatchService, rightsholderService);
    }

    @Test
    public void testGetRros() {
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).once();
        List<Rightsholder> rightsholders = List.of(new Rightsholder());
        expect(rightsholderService.getRros(FAS_PRODUCT_FAMILY)).andReturn(rightsholders).once();
        replay(productFamilyProvider, usageBatchService, rightsholderService);
        assertSame(rightsholders, controller.getRros());
        verify(productFamilyProvider, usageBatchService, rightsholderService);
    }
}
