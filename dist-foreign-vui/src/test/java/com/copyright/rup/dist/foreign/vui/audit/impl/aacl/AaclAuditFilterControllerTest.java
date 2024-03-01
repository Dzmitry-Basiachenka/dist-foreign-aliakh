package com.copyright.rup.dist.foreign.vui.audit.impl.aacl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isNull;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.vui.audit.impl.CommonAuditFilterController;
import com.copyright.rup.dist.foreign.vui.main.api.IProductFamilyProvider;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.List;

/**
 * Verifies {@link AaclAuditFilterController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/12/2020
 *
 * @author Anton Azarenka
 */
public class AaclAuditFilterControllerTest {

    private static final String AACL_PRODUCT_FAMILY = "AACL";

    private CommonAuditFilterController controller;
    private IUsageBatchService usageBatchService;
    private IRightsholderService rightsholderService;
    private IProductFamilyProvider productFamilyProvider;

    @Before
    public void setUp() {
        controller = new AaclAuditFilterController();
        usageBatchService = createMock(IUsageBatchService.class);
        rightsholderService = createMock(IRightsholderService.class);
        productFamilyProvider = createMock(IProductFamilyProvider.class);
        Whitebox.setInternalState(controller, usageBatchService);
        Whitebox.setInternalState(controller, rightsholderService);
        Whitebox.setInternalState(controller, productFamilyProvider);
    }

    @Test
    public void testLoadBeans() {
        List<Rightsholder> rightsholders = List.of();
        Capture<Pageable> pageableCapture = newCapture();
        expect(rightsholderService.getAllWithSearch(eq("search"), capture(pageableCapture), isNull()))
            .andReturn(rightsholders).once();
        replay(rightsholderService);
        assertSame(rightsholders, controller.loadBeans("search", 0, 10, null));
        assertEquals(10, pageableCapture.getValue().getLimit());
        assertEquals(0, pageableCapture.getValue().getOffset());
        verify(rightsholderService);
    }

    @Test
    public void testGetBeansCount() {
        expect(rightsholderService.getCountWithSearch("searchValue")).andReturn(10).once();
        replay(rightsholderService);
        assertEquals(10, controller.getBeansCount("searchValue"));
        verify(rightsholderService);
    }

    @Test
    public void testGetUsageBatches() {
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(AACL_PRODUCT_FAMILY).once();
        List<UsageBatch> usageBatches = List.of();
        expect(usageBatchService.getUsageBatches(AACL_PRODUCT_FAMILY)).andReturn(usageBatches).once();
        replay(usageBatchService, productFamilyProvider);
        assertSame(usageBatches, controller.getUsageBatches());
        verify(usageBatchService, productFamilyProvider);
    }

    @Test
    public void testGetProductFamily() {
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(AACL_PRODUCT_FAMILY).once();
        replay(productFamilyProvider);
        assertEquals(AACL_PRODUCT_FAMILY, controller.getProductFamily());
        verify(productFamilyProvider);
    }
}
