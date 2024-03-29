package com.copyright.rup.dist.foreign.vui.report.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.createMock;

import com.copyright.rup.dist.foreign.vui.main.api.IProductFamilyProvider;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

/**
 * Verifies {@link ReportController}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 5/10/2018
 *
 * @author Uladzislau_Shalamitski
 */
public class ReportControllerTest {

    private ReportController reportController;
    private IProductFamilyProvider productFamilyProvider;

    @Before
    public void setUp() {
        reportController = new ReportController();
        productFamilyProvider = createMock(IProductFamilyProvider.class);
        Whitebox.setInternalState(reportController, productFamilyProvider);
    }

    @Test
    public void testProductFamilyProvider() {
        assertSame(productFamilyProvider, reportController.getProductFamilyProvider());
    }

    @Test
    public void testInstantiateWidget() {
        var widget = reportController.instantiateWidget();
        assertNotNull(reportController.instantiateWidget());
        assertEquals(ReportWidget.class, widget.getClass());
    }
}
