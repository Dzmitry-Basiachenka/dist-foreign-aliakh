package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.ui.main.api.IProductFamilyProvider;

import com.google.common.collect.Lists;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
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

    private static final Integer FISCAL_YEAR = 2017;
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
        expect(usageBatchService.getFiscalYears(FAS_PRODUCT_FAMILY))
            .andReturn(Collections.singletonList(FISCAL_YEAR)).once();
        replay(usageBatchService, productFamilyProvider);
        List<Integer> fiscalYears = controller.getFiscalYears();
        assertEquals(1, CollectionUtils.size(fiscalYears));
        assertTrue(fiscalYears.contains(FISCAL_YEAR));
        verify(usageBatchService, productFamilyProvider);
    }

    @Test
    public void testGetUsageBatchesForFilter() {
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).once();
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setName("name");
        expect(usageBatchService.getUsageBatches(FAS_PRODUCT_FAMILY)).andReturn(Lists.newArrayList(usageBatch)).once();
        replay(usageBatchService, productFamilyProvider);
        List<UsageBatch> usageBatches = controller.getUsageBatches();
        assertEquals(1, CollectionUtils.size(usageBatches));
        assertEquals(usageBatch.getName(), usageBatches.iterator().next().getName());
        verify(usageBatchService, productFamilyProvider);
    }

    @Test
    public void testGetRros() {
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).once();
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(12345678L);
        expect(rightsholderService.getRros(FAS_PRODUCT_FAMILY)).andReturn(Lists.newArrayList(rightsholder)).once();
        replay(rightsholderService, productFamilyProvider);
        List<Rightsholder> rightsholders = controller.getRros();
        assertEquals(1, CollectionUtils.size(rightsholders));
        assertEquals(rightsholder.getAccountNumber(), rightsholders.iterator().next().getAccountNumber());
        verify(rightsholderService, productFamilyProvider);
    }
}
