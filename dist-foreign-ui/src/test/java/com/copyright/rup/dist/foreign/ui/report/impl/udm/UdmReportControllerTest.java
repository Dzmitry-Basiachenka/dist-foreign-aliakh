package com.copyright.rup.dist.foreign.ui.report.impl.udm;

import static org.easymock.EasyMock.createMock;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmCommonUserNamesReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmSurveyLicenseeReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmUsableDetailsByCountryReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmUsageEditsInBaselineReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmVerifiedDetailsBySourceReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmWeeklySurveyReportController;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

/**
 * Verifies {@link UdmReportController}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/16/2021
 *
 * @author Aliaksandr Liakh
 */
public class UdmReportControllerTest {

    private IUdmWeeklySurveyReportController udmWeeklySurveyReportController;
    private IUdmCommonUserNamesReportController completedAssignmentsReportController;
    private IUdmSurveyLicenseeReportController udmSurveyLicenseeReportController;
    private IUdmVerifiedDetailsBySourceReportController udmVerifiedDetailsBySourceReportController;
    private IUdmUsableDetailsByCountryReportController udmUsableDetailsByCountryReportController;
    private IUdmUsageEditsInBaselineReportController udmUsageEditsInBaselineReportController;
    private IUdmCommonUserNamesReportController udmBaselineValueUpdatesReportController;
    private UdmReportController udmReportController;

    @Before
    public void setUp() {
        udmReportController = new UdmReportController();
        udmWeeklySurveyReportController = createMock(IUdmWeeklySurveyReportController.class);
        completedAssignmentsReportController = createMock(IUdmCommonUserNamesReportController.class);
        udmSurveyLicenseeReportController = createMock(IUdmSurveyLicenseeReportController.class);
        udmVerifiedDetailsBySourceReportController = createMock(IUdmVerifiedDetailsBySourceReportController.class);
        udmUsableDetailsByCountryReportController = createMock(IUdmUsableDetailsByCountryReportController.class);
        udmUsageEditsInBaselineReportController = createMock(IUdmUsageEditsInBaselineReportController.class);
        udmBaselineValueUpdatesReportController = createMock(IUdmCommonUserNamesReportController.class);
        Whitebox.setInternalState(udmReportController, udmWeeklySurveyReportController);
        Whitebox.setInternalState(udmReportController, "completedAssignmentsReportController",
            completedAssignmentsReportController);
        Whitebox.setInternalState(udmReportController, udmSurveyLicenseeReportController);
        Whitebox.setInternalState(udmReportController, udmVerifiedDetailsBySourceReportController);
        Whitebox.setInternalState(udmReportController, udmUsableDetailsByCountryReportController);
        Whitebox.setInternalState(udmReportController, udmUsageEditsInBaselineReportController);
        Whitebox.setInternalState(udmReportController, "udmBaselineValueUpdatesReportController",
            udmBaselineValueUpdatesReportController);
    }

    @Test
    public void testGetUdmWeeklySurveyReportController() {
        assertSame(udmWeeklySurveyReportController, udmReportController.getUdmWeeklySurveyReportController());
    }

    @Test
    public void testGetCompletedAssignmentsReportController() {
        assertSame(completedAssignmentsReportController, udmReportController.getCompletedAssignmentsReportController());
    }

    @Test
    public void testGetUdmSurveyLicenseeReportController() {
        assertSame(udmSurveyLicenseeReportController, udmReportController.getUdmSurveyLicenseeReportController());
    }

    @Test
    public void testGetUdmVerifiedDetailsBySourceReportController() {
        assertSame(udmVerifiedDetailsBySourceReportController,
            udmReportController.getUdmVerifiedDetailsBySourceReportController());
    }

    @Test
    public void testGetUdmUsableDetailsByCountryReportController() {
        assertSame(udmUsableDetailsByCountryReportController,
            udmReportController.getUdmUsableDetailsByCountryReportController());
    }

    @Test
    public void testGetUdmUsageEditsInBaselineReportController() {
        assertSame(udmUsageEditsInBaselineReportController,
            udmReportController.getUdmUsageEditsInBaselineReportController());
    }

    @Test
    public void testGetUdmBaselineValueUpdatesReportController() {
        assertSame(udmBaselineValueUpdatesReportController,
            udmReportController.getUdmBaselineValueUpdatesReportController());
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(udmReportController.instantiateWidget(), instanceOf(UdmReportWidget.class));
    }
}
