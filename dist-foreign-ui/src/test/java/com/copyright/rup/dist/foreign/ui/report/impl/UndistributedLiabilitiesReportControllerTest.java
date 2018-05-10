package com.copyright.rup.dist.foreign.ui.report.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.ui.report.api.IUndistributedLiabilitiesReportWidget;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

/**
 * Verifies {@link UndistributedLiabilitiesReportController}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 5/10/2018
 *
 * @author Uladzislau_Shalamitski
 */
public class UndistributedLiabilitiesReportControllerTest {

    private UndistributedLiabilitiesReportController controller;

    @Before
    public void setUp() {
        controller = new UndistributedLiabilitiesReportController();
    }

    @Test
    public void testInstantiateWidget() {
        IUndistributedLiabilitiesReportWidget widget = controller.instantiateWidget();
        assertNotNull(controller.instantiateWidget());
        assertEquals(UndistributedLiabilitiesReportWidget.class, widget.getClass());
    }

    @Test
    public void testSendForResearchUsagesStreamSourceFileName() {
        assertEquals("undistributed_liabilities_" + CommonDateUtils.format(LocalDate.now(), "MM_dd_YYYY") + ".csv",
            controller.getUndistributedLiabilitiesReportStreamSource().getFileName());
    }
}
