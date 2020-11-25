package com.copyright.rup.dist.foreign.ui.report.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.foreign.ui.report.api.ISalHistoricalItemBankDetailsReportWidget;
import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link SalHistoricalItemBankDetailsReportController}.
 * <p>
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 11/25/2020
 *
 * @author Aliaksandr Liakh
 */
public class SalHistoricalItemBankDetailsReportControllerTest {

    private SalHistoricalItemBankDetailsReportController controller;

    @Before
    public void setUp() {
        controller = new SalHistoricalItemBankDetailsReportController();
    }

    @Test
    public void testInstantiateWidget() {
        ISalHistoricalItemBankDetailsReportWidget widget = controller.instantiateWidget();
        assertNotNull(controller.instantiateWidget());
        assertEquals(SalHistoricalItemBankDetailsReportWidget.class, widget.getClass());
    }
}
