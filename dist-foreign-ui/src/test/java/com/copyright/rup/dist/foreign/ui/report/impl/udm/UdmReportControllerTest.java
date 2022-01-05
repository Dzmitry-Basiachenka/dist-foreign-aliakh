package com.copyright.rup.dist.foreign.ui.report.impl.udm;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.report.api.IUdmWeeklySurveyReportController;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.time.OffsetDateTime;

/**
 * Verifies {@link UdmCommonReportController}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/16/2021
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({OffsetDateTime.class, ByteArrayStreamSource.class})
public class UdmReportControllerTest {

    private IUdmWeeklySurveyReportController udmWeeklySurveyReportController;
    private UdmCommonReportController udmReportController;

    @Before
    public void setUp() {
        udmReportController = new UdmCommonReportController();
        udmWeeklySurveyReportController = createMock(IUdmWeeklySurveyReportController.class);
        Whitebox.setInternalState(udmReportController, udmWeeklySurveyReportController);
    }

    @Test
    public void testGetUdmWeeklySurveyReportController() {
        assertSame(udmWeeklySurveyReportController, udmReportController.getUdmWeeklySurveyReportController());
    }
}
