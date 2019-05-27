package com.copyright.rup.dist.foreign.ui.report.impl;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.ui.common.ExportStreamSource.IReportWriter;

import org.junit.Test;

/**
 * Verifies {@link OwnershipAdjustmentCsvReportExportStreamSource}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 05/27/2019
 *
 * @author Aliaksandr Liakh
 */
public class OwnershipAdjustmentCsvReportExportStreamSourceTest {

    @Test
    public void testGetFileNamePrefix() {
        Scenario scenario = new Scenario();
        scenario.setId(RupPersistUtils.generateUuid());
        scenario.setName("Test scenario name");
        OwnershipAdjustmentCsvReportExportStreamSource streamSource =
            new OwnershipAdjustmentCsvReportExportStreamSource(() -> scenario, createMock(IReportWriter.class));
        assertEquals("ownership_adjustment_report_Test scenario name_", streamSource.getFileNamePrefix());
    }
}
