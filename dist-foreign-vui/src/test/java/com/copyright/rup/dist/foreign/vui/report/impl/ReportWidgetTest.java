package com.copyright.rup.dist.foreign.vui.report.impl;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verifyAll;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.vui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.vui.report.api.IReportController;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link ReportWidget}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 5/10/2018
 *
 * @author Uladzislau_Shalamitski
 */
public class ReportWidgetTest {

    private ReportWidget reportWidget;
    private IReportController reportController;

    @Before
    public void setUp() {
        reportWidget = new ReportWidget();
        reportController = createMock(IReportController.class);
        reportWidget.setController(reportController);
    }

    @Test
    public void testInit() {
        expectProductFamily(FdaConstants.FAS_PRODUCT_FAMILY);
        replayAll();
        reportWidget.init();
        assertEquals("reports-menu-root", reportWidget.getClassName());
    }

    @Test
    public void testRefreshProductFamilyFas() {
        expectProductFamily(FdaConstants.FAS_PRODUCT_FAMILY);
        replayAll();
        reportWidget.refresh();
        verifyAll();
    }

    @Test
    public void testRefreshProductFamilyFas2() {
        expectProductFamily(FdaConstants.CLA_FAS_PRODUCT_FAMILY);
        replayAll();
        reportWidget.refresh();
        verifyAll();
    }

    private void expectProductFamily(String productFamily) {
        IProductFamilyProvider productFamilyProvider = createMock(IProductFamilyProvider.class);
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(productFamily).once();
        expect(reportController.getProductFamilyProvider()).andReturn(productFamilyProvider).once();
    }
}
