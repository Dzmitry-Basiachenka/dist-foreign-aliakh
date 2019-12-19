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
import com.copyright.rup.dist.foreign.ui.main.api.IProductFamilyProvider;

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
    private IProductFamilyProvider productFamilyProvider;

    private static final String FAS_PRODUCT_FAMILY = "FAS";

    @Before
    public void setUp() {
        controller = new AuditFilterController();
        usageBatchService = createMock(IUsageBatchService.class);
        rightsholderService = createMock(IRightsholderService.class);
        productFamilyProvider = createMock(IProductFamilyProvider.class);
        Whitebox.setInternalState(controller, usageBatchService);
        Whitebox.setInternalState(controller, rightsholderService);
        Whitebox.setInternalState(controller, productFamilyProvider);
    }

    @Test
    public void testGetRightsholders() {
        List<Rightsholder> rightsholders = Collections.emptyList();
        Capture<Pageable> pageableCapture = new Capture<>();
        expect(rightsholderService.getFromUsages(
            eq(FAS_PRODUCT_FAMILY), eq("search"), capture(pageableCapture), isNull()))
            .andReturn(rightsholders).once();
        replay(rightsholderService);
        assertSame(rightsholders, controller.loadBeans(FAS_PRODUCT_FAMILY, "search", 0, 10, null));
        assertEquals(10, pageableCapture.getValue().getLimit());
        assertEquals(0, pageableCapture.getValue().getOffset());
        verify(rightsholderService);
    }

    @Test
    public void testGetUsageBatches() {
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).once();
        List<UsageBatch> usageBatches = Collections.emptyList();
        expect(usageBatchService.getUsageBatchesByProductFamilies(Collections.singleton(FAS_PRODUCT_FAMILY)))
            .andReturn(usageBatches).once();
        replay(usageBatchService, productFamilyProvider);
        assertSame(usageBatches, controller.getUsageBatches());
        verify(usageBatchService, productFamilyProvider);
    }

    @Test
    public void testInstantiateWidget() {
        assertTrue(controller.instantiateWidget() instanceof AuditFilterWidget);
    }

    @Test
    public void testGetProductFamily() {
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).once();
        replay(productFamilyProvider);
        assertEquals(FAS_PRODUCT_FAMILY, controller.getProductFamily());
        verify(productFamilyProvider);
    }
}
