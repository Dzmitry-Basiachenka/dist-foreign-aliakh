package com.copyright.rup.dist.foreign.ui.report.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.same;
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
import com.copyright.rup.dist.foreign.ui.report.api.IScenarioReportWidget;

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
 * Verifies {@link NtsServiceFeeTrueUpReportController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/31/2020
 *
 * @author Stanislau Rudak
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({OffsetDateTime.class, ByteArrayStreamSource.class})
public class NtsServiceFeeTrueUpReportControllerTest {

    private IScenarioService scenarioService;
    private IReportService reportService;
    private NtsServiceFeeTrueUpReportController controller;

    @Before
    public void setUp() {
        controller = new NtsServiceFeeTrueUpReportController();
        scenarioService = createMock(IScenarioService.class);
        reportService = createMock(IReportService.class);
        Whitebox.setInternalState(controller, scenarioService);
        Whitebox.setInternalState(controller, reportService);
    }

    @Test
    public void testGetScenarios() {
        List<Scenario> scenarios = Collections.emptyList();
        expect(scenarioService.getScenariosByProductFamiliesAndStatuses(
            Collections.singleton("NTS"), Collections.singleton(ScenarioStatusEnum.SENT_TO_LM)))
            .andReturn(scenarios).once();
        replay(scenarioService);
        assertEquals(scenarios, controller.getScenarios());
        verify(scenarioService);
    }

    @Test
    public void testGetCsvStreamSource() {
        OffsetDateTime now = OffsetDateTime.of(2019, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
        mockStatic(OffsetDateTime.class);
        Scenario scenario = new Scenario();
        IScenarioReportWidget widget = createMock(IScenarioReportWidget.class);
        Whitebox.setInternalState(controller, widget);
        Capture<OutputStream> osCapture = new Capture<>();
        expect(OffsetDateTime.now()).andReturn(now).once();
        expect(widget.getScenario()).andReturn(scenario).once();
        reportService.writeNtsServiceFeeTrueUpCsvReport(same(scenario), capture(osCapture));
        expectLastCall().once();
        replay(OffsetDateTime.class, widget, reportService);
        IStreamSource streamSource = controller.getCsvStreamSource();
        assertEquals("service_fee_true_up_report_01_02_2019_03_04.csv",
            streamSource.getSource().getKey().get());
        assertNotNull(streamSource.getSource().getValue().get());
        assertNotNull(osCapture.getValue());
        verify(OffsetDateTime.class, widget, reportService);
    }

    @Test
    public void testInstantiateWidget() {
        IScenarioReportWidget widget = controller.instantiateWidget();
        assertNotNull(controller.instantiateWidget());
        assertEquals(ScenarioReportWidget.class, widget.getClass());
    }
}