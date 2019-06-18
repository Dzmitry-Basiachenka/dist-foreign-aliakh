package com.copyright.rup.dist.foreign.ui.report.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.ui.report.api.IOwnershipAdjustmentReportWidget;
import com.copyright.rup.dist.foreign.ui.report.api.IStreamSourceHandler;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;

import com.google.common.collect.ImmutableSet;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.easymock.Capture;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.io.InputStream;
import java.io.PipedOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Verifies {@link OwnershipAdjustmentReportController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 05/27/2019
 *
 * @author Aliaksandr Liakh
 */
public class OwnershipAdjustmentReportControllerTest {

    @Test
    public void testGetScenarios() {
        OwnershipAdjustmentReportController controller = new OwnershipAdjustmentReportController();
        IScenarioService scenarioService = createMock(IScenarioService.class);
        Whitebox.setInternalState(controller, scenarioService);
        List<Scenario> scenarios = Collections.emptyList();
        expect(scenarioService.getScenarios()).andReturn(scenarios).once();
        replay(scenarioService);
        assertEquals(scenarios, controller.getScenarios());
        verify(scenarioService);
    }

    @Test
    public void testGetCsvStreamSource() {
        OwnershipAdjustmentReportController controller = new OwnershipAdjustmentReportController();
        IOwnershipAdjustmentReportWidget widget = createMock(IOwnershipAdjustmentReportWidget.class);
        IReportService reportService = createMock(IReportService.class);
        IStreamSourceHandler streamSourceHandler = createMock(IStreamSourceHandler.class);
        Whitebox.setInternalState(controller, widget);
        Whitebox.setInternalState(controller, reportService);
        Whitebox.setInternalState(controller, streamSourceHandler);
        Scenario scenario = new Scenario();
        scenario.setId(RupPersistUtils.generateUuid());
        scenario.setName("Scenario name");
        Capture<Supplier<String>> fileNameSupplierCapture = new Capture<>();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = new Capture<>();
        String fileName = String.format("ownership_adjustment_report_%s_", scenario.getName());
        Supplier<String> fileNameSupplier = () -> fileName;
        Supplier<InputStream> isSupplier = () -> IOUtils.toInputStream(StringUtils.EMPTY, StandardCharsets.UTF_8);
        PipedOutputStream pos = new PipedOutputStream();
        expect(widget.getScenario()).andReturn(scenario).times(2);
        expect(streamSourceHandler.getCsvStreamSource(capture(fileNameSupplierCapture), capture(posConsumerCapture)))
            .andReturn(new StreamSource(fileNameSupplier, "csv", isSupplier)).once();
        reportService.writeOwnershipAdjustmentCsvReport(scenario.getId(),
            ImmutableSet.of(RightsholderDiscrepancyStatusEnum.DRAFT, RightsholderDiscrepancyStatusEnum.APPROVED), pos);
        expectLastCall().once();
        replay(widget, streamSourceHandler, reportService);
        IStreamSource streamSource = controller.getCsvStreamSource();
        assertEquals("ownership_adjustment_report_Scenario_name_" +
            CommonDateUtils.format(OffsetDateTime.now(), "MM_dd_YYYY_HH_mm") + ".csv", streamSource.getFileName());
        assertEquals(fileName, fileNameSupplierCapture.getValue().get());
        Consumer<PipedOutputStream> posConsumer = posConsumerCapture.getValue();
        posConsumer.accept(pos);
        verify(widget, streamSourceHandler, reportService);
    }

    @Test
    public void testInstantiateWidget() {
        OwnershipAdjustmentReportController controller = new OwnershipAdjustmentReportController();
        IOwnershipAdjustmentReportWidget widget = controller.instantiateWidget();
        assertNotNull(controller.instantiateWidget());
        assertEquals(OwnershipAdjustmentReportWidget.class, widget.getClass());
    }
}
