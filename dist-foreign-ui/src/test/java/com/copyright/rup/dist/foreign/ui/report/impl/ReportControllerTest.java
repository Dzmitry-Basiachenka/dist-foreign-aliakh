package com.copyright.rup.dist.foreign.ui.report.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.foreign.ui.report.api.IReportWidget;
import com.copyright.rup.dist.foreign.ui.report.api.IUndistributedLiabilitiesReportController;

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

    private IUndistributedLiabilitiesReportController undistributedLiabilitiesReportController;
    private ReportController reportController;

    @Before
    public void setUp() {
        reportController = new ReportController();
        undistributedLiabilitiesReportController = new UndistributedLiabilitiesReportController();
        Whitebox.setInternalState(reportController, "undistributedLiabilitiesReportController",
            undistributedLiabilitiesReportController);
    }

    @Test
    public void testGetUndistributedLiabilitiesReportController() {
        assertSame(undistributedLiabilitiesReportController,
            reportController.getUndistributedLiabilitiesReportController());
    }

    @Test
    public void testInstantiateWidget() {
        IReportWidget widget = reportController.instantiateWidget();
        assertNotNull(reportController.instantiateWidget());
        assertEquals(ReportWidget.class, widget.getClass());
    }
}
