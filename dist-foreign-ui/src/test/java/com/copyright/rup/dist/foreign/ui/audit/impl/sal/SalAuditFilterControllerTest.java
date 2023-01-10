package com.copyright.rup.dist.foreign.ui.audit.impl.sal;

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
import com.copyright.rup.dist.foreign.domain.report.SalLicensee;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.ui.audit.api.sal.ISalAuditFilterController;
import com.copyright.rup.dist.foreign.ui.main.api.IProductFamilyProvider;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.List;

/**
 * Verifies {@link SalAuditFilterController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 12/16/2020
 *
 * @author Aliaksandr Liakh
 */
public class SalAuditFilterControllerTest {

    private static final String SAL_PRODUCT_FAMILY = "SAL";
    private static final String SEARCH_VALUE = "searchValue";

    private ISalAuditFilterController controller;
    private IUsageBatchService usageBatchService;
    private IRightsholderService rightsholderService;
    private IProductFamilyProvider productFamilyProvider;

    @Before
    public void setUp() {
        controller = new SalAuditFilterController();
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
        expect(rightsholderService.getAllWithSearch(eq(SEARCH_VALUE), capture(pageableCapture), isNull()))
            .andReturn(rightsholders).once();
        replay(rightsholderService);
        assertSame(rightsholders, controller.loadBeans(SEARCH_VALUE, 0, 10, null));
        assertEquals(10, pageableCapture.getValue().getLimit());
        assertEquals(0, pageableCapture.getValue().getOffset());
        verify(rightsholderService);
    }

    @Test
    public void testGetBeansCount() {
        expect(rightsholderService.getCountWithSearch(SEARCH_VALUE)).andReturn(10).once();
        replay(rightsholderService);
        assertEquals(10, controller.getBeansCount(SEARCH_VALUE));
        verify(rightsholderService);
    }

    @Test
    public void testGetProductFamily() {
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(SAL_PRODUCT_FAMILY).once();
        replay(productFamilyProvider);
        assertEquals(SAL_PRODUCT_FAMILY, controller.getProductFamily());
        verify(productFamilyProvider);
    }

    @Test
    public void testGetUsageBatches() {
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(SAL_PRODUCT_FAMILY).once();
        List<UsageBatch> usageBatches = List.of();
        expect(usageBatchService.getUsageBatches(SAL_PRODUCT_FAMILY)).andReturn(usageBatches).once();
        replay(usageBatchService, productFamilyProvider);
        assertSame(usageBatches, controller.getUsageBatches());
        verify(usageBatchService, productFamilyProvider);
    }

    @Test
    public void testGetSalLicensees() {
        List<SalLicensee> licensees = List.of(new SalLicensee());
        expect(usageBatchService.getSalLicensees()).andReturn(licensees).once();
        replay(usageBatchService);
        assertSame(licensees, controller.getSalLicensees());
        verify(usageBatchService);
    }
}
