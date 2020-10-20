package com.copyright.rup.dist.foreign.ui.report.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.ui.report.api.ICommonScenariosReportWidget;

import com.google.common.collect.Sets;
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
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link SalLiabilitiesByRhReportController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 10/14/20
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({OffsetDateTime.class, ByteArrayStreamSource.class})
public class SalLiabilitiesByRhReportControllerTest {

    private IScenarioService scenarioService;
    private IReportService reportService;
    private IProductFamilyProvider productFamilyProvider;
    private SalLiabilitiesByRhReportController controller;

    @Before
    public void setUp() {
        controller = new SalLiabilitiesByRhReportController();
        scenarioService = createMock(IScenarioService.class);
        reportService = createMock(IReportService.class);
        productFamilyProvider = createMock(IProductFamilyProvider.class);
        Whitebox.setInternalState(controller, scenarioService);
        Whitebox.setInternalState(controller, reportService);
        Whitebox.setInternalState(controller, productFamilyProvider);
    }

    @Test
    public void testInstantiateWidget() {
        ICommonScenariosReportWidget widget = controller.instantiateWidget();
        assertNotNull(controller.instantiateWidget());
        assertEquals(CommonScenariosReportWidget.class, widget.getClass());
    }

    @Test
    public void testGetCsvStreamSource() {
        OffsetDateTime now = OffsetDateTime.of(2019, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
        mockStatic(OffsetDateTime.class);
        ICommonScenariosReportWidget widget = createMock(ICommonScenariosReportWidget.class);
        Whitebox.setInternalState(controller, widget);
        Capture<OutputStream> outputStreamCapture = new Capture<>();
        List<Scenario> scenarios = Collections.singletonList(new Scenario());
        expect(OffsetDateTime.now()).andReturn(now).once();
        expect(widget.getSelectedScenarios()).andReturn(scenarios).once();
        reportService
            .writeSalLiabilitiesByRhReport(eq(scenarios), capture(outputStreamCapture));
        expectLastCall().once();
        replay(OffsetDateTime.class, widget, reportService);
        IStreamSource streamSource = controller.getCsvStreamSource();
        assertEquals("liabilities_by_rightsholder_01_02_2019_03_04.csv", streamSource.getSource().getKey().get());
        assertNotNull(streamSource.getSource().getValue().get());
        assertNotNull(outputStreamCapture.getValue());
        verify(OffsetDateTime.class, widget, reportService);
    }

    @Test
    public void testGetScenarios() {
        List<Scenario> scenarios = Collections.singletonList(new Scenario());
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn("SAL").once();
        expect(scenarioService.getScenariosByProductFamiliesAndStatuses(Sets.newHashSet("SAL"),
            Sets.newHashSet(ScenarioStatusEnum.values()))).andReturn(scenarios).once();
        replay(scenarioService, productFamilyProvider);
        assertEquals(scenarios, controller.getScenarios());
        verify(scenarioService, productFamilyProvider);
    }
}
