package com.copyright.rup.dist.foreign.vui.status.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.foreign.domain.UsageBatchStatus;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchStatusService;
import com.copyright.rup.dist.foreign.vui.main.api.IProductFamilyProvider;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.List;

/**
 * Verifies {@link FasBatchStatusController}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 02/12/2021
 *
 * @author Darya Baraukova
 */
public class FasBatchStatusControllerTest {

    private static final String FAS_PRODUCT_FAMILY = "FAS";

    private FasBatchStatusController controller;
    private IUsageBatchStatusService usageBatchStatusService;
    private IProductFamilyProvider productFamilyProvider;

    @Before
    public void setUp() {
        usageBatchStatusService = createMock(IUsageBatchStatusService.class);
        productFamilyProvider = createMock(IProductFamilyProvider.class);
        controller = new FasBatchStatusController();
        Whitebox.setInternalState(controller, usageBatchStatusService);
        Whitebox.setInternalState(controller, productFamilyProvider);
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(controller.instantiateWidget(), instanceOf(FasBatchStatusWidget.class));
    }

    @Test
    public void testGetBatchStatuses() {
        var batchStatuses = List.of(new UsageBatchStatus());
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).once();
        expect(usageBatchStatusService.getUsageBatchStatusesFas(FAS_PRODUCT_FAMILY)).andReturn(batchStatuses).once();
        replay(productFamilyProvider, usageBatchStatusService);
        assertSame(batchStatuses, controller.getBatchStatuses());
        verify(productFamilyProvider, usageBatchStatusService);
    }
}
