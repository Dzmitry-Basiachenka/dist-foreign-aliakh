package com.copyright.rup.dist.foreign.ui.report.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioService;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.ui.report.api.ITaxNotificationReportWidget;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.OutputStream;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link TaxNotificationReportController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 6/24/20
 *
 * @author Stanislau Rudak
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({OffsetDateTime.class, ByteArrayStreamSource.class})
public class TaxNotificationReportControllerTest {

    private static final Set<ScenarioStatusEnum> SCENARIO_STATUSES =
        EnumSet.of(ScenarioStatusEnum.IN_PROGRESS, ScenarioStatusEnum.SUBMITTED, ScenarioStatusEnum.APPROVED);
    private static final String PRODUCT_FAMILY_ACL = "ACL";

    private IReportService reportService;
    private IScenarioService scenarioService;
    private IAclScenarioService aclScenarioService;
    private IProductFamilyProvider productFamilyProvider;
    private TaxNotificationReportController controller;

    @Before
    public void setUp() {
        controller = new TaxNotificationReportController();
        reportService = createMock(IReportService.class);
        scenarioService = createMock(IScenarioService.class);
        aclScenarioService = createMock(IAclScenarioService.class);
        productFamilyProvider = createMock(IProductFamilyProvider.class);
        Whitebox.setInternalState(controller, reportService);
        Whitebox.setInternalState(controller, scenarioService);
        Whitebox.setInternalState(controller, aclScenarioService);
        Whitebox.setInternalState(controller, productFamilyProvider);
    }

    @Test
    public void testInstantiateWidget() {
        ITaxNotificationReportWidget widget = controller.instantiateWidget();
        assertNotNull(controller.instantiateWidget());
        assertEquals(TaxNotificationReportWidget.class, widget.getClass());
    }

    @Test
    public void testGetCsvStreamSource() {
        OffsetDateTime now = OffsetDateTime.of(2019, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
        mockStatic(OffsetDateTime.class);
        Set<String> scenarioIds = Set.of("090bdd49-099b-4613-a979-9a3ac014231b");
        Capture<OutputStream> osCapture = newCapture();
        ITaxNotificationReportWidget widget = createMock(ITaxNotificationReportWidget.class);
        Whitebox.setInternalState(controller, widget);
        expect(OffsetDateTime.now()).andReturn(now).once();
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(PRODUCT_FAMILY_ACL).once();
        expect(widget.getSelectedScenarioIds()).andReturn(scenarioIds).once();
        expect(widget.getNumberOfDays()).andReturn(15).once();
        reportService.writeTaxNotificationCsvReport(eq(PRODUCT_FAMILY_ACL), eq(scenarioIds), eq(15),
            capture(osCapture));
        expectLastCall().once();
        replay(OffsetDateTime.class, widget, reportService, productFamilyProvider);
        IStreamSource streamSource = controller.getCsvStreamSource();
        assertEquals("tax_notification_report_01_02_2019_03_04.csv", streamSource.getSource().getKey().get());
        assertNotNull(streamSource.getSource().getValue().get());
        assertNotNull(osCapture.getValue());
        verify(OffsetDateTime.class, widget, reportService, productFamilyProvider);
    }

    @Test
    public void testGetScenariosFas() {
        List<Scenario> scenarios = List.of(new Scenario());
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn("FAS").once();
        expect(scenarioService.getScenariosByProductFamiliesAndStatuses(Set.of("FAS", "FAS2"), SCENARIO_STATUSES))
            .andReturn(scenarios).once();
        replay(scenarioService, productFamilyProvider);
        assertEquals(scenarios, controller.getScenarios());
        verify(scenarioService, productFamilyProvider);
    }

    @Test
    public void testGetScenariosFas2() {
        List<Scenario> scenarios = List.of(new Scenario());
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn("FAS2").once();
        expect(scenarioService.getScenariosByProductFamiliesAndStatuses(Set.of("FAS", "FAS2"), SCENARIO_STATUSES))
            .andReturn(scenarios).once();
        replay(scenarioService, productFamilyProvider);
        assertEquals(scenarios, controller.getScenarios());
        verify(scenarioService, productFamilyProvider);
    }

    @Test
    public void testGetScenariosNts() {
        List<Scenario> scenarios = List.of(new Scenario());
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn("NTS").once();
        expect(
            scenarioService.getScenariosByProductFamiliesAndStatuses(Set.of("NTS"), SCENARIO_STATUSES))
            .andReturn(scenarios).once();
        replay(scenarioService, productFamilyProvider);
        assertEquals(scenarios, controller.getScenarios());
        verify(scenarioService, productFamilyProvider);
    }

    @Test
    public void testGetScenariosAcl() {
        List<Scenario> scenarios = List.of(new Scenario());
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(PRODUCT_FAMILY_ACL).once();
        expect(aclScenarioService.getAclScenariosByStatuses(SCENARIO_STATUSES)).andReturn(scenarios).once();
        replay(aclScenarioService, productFamilyProvider);
        assertSame(scenarios, controller.getScenarios());
        verify(aclScenarioService, productFamilyProvider);
    }
}
