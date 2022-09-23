package com.copyright.rup.dist.foreign.ui.report.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.reporting.impl.StreamSource;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.ui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.ui.report.api.ICommonScenarioReportWidget;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.easymock.Capture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.InputStream;
import java.io.PipedOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Verifies {@link WorkSharesByAggLcClassReportController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 04/22/2020
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({OffsetDateTime.class, StreamSource.class})
public class WorkSharesByAggLcClassReportControllerTest {

    @Test
    public void testGetScenarios() {
        WorkSharesByAggLcClassReportController controller = new WorkSharesByAggLcClassReportController();
        IScenarioService scenarioService = createMock(IScenarioService.class);
        IProductFamilyProvider productFamilyProvider = createMock(IProductFamilyProvider.class);
        Whitebox.setInternalState(controller, scenarioService);
        Whitebox.setInternalState(controller, productFamilyProvider);
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn("AACL").once();
        List<Scenario> scenarios = Collections.emptyList();
        expect(scenarioService.getScenarios("AACL")).andReturn(scenarios).once();
        replay(scenarioService, productFamilyProvider);
        assertEquals(scenarios, controller.getScenarios());
        verify(scenarioService, productFamilyProvider);
    }

    @Test
    public void testGetCsvStreamSource() {
        OffsetDateTime now = OffsetDateTime.of(2019, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
        mockStatic(OffsetDateTime.class);
        WorkSharesByAggLcClassReportController controller = new WorkSharesByAggLcClassReportController();
        ICommonScenarioReportWidget widget = createMock(ICommonScenarioReportWidget.class);
        IStreamSourceHandler streamSourceHandler = createMock(IStreamSourceHandler.class);
        IReportService reportService = createMock(IReportService.class);
        Whitebox.setInternalState(controller, widget);
        Whitebox.setInternalState(controller, reportService);
        Whitebox.setInternalState(controller, streamSourceHandler);
        Scenario scenario = new Scenario();
        scenario.setId(RupPersistUtils.generateUuid());
        scenario.setName("Scenario name");
        Capture<Supplier<String>> fileNameSupplierCapture = new Capture<>();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = new Capture<>();
        String fileName = String.format("work_shares_by_agg_lc_class_report_%s_", scenario.getName());
        Supplier<String> fileNameSupplier = () -> fileName;
        Supplier<InputStream> inputStreamSupplier =
                () -> IOUtils.toInputStream(StringUtils.EMPTY, StandardCharsets.UTF_8);
        PipedOutputStream pos = new PipedOutputStream();
        expect(OffsetDateTime.now()).andReturn(now).once();
        expect(widget.getScenario()).andReturn(scenario).times(2);
        expect(streamSourceHandler.getCsvStreamSource(capture(fileNameSupplierCapture), capture(posConsumerCapture)))
            .andReturn(new StreamSource(fileNameSupplier, "csv", inputStreamSupplier)).once();
        reportService.writeWorkSharesByAggLcClassCsvReport(scenario, pos);
        expectLastCall().once();
        replay(OffsetDateTime.class, widget, streamSourceHandler);
        IStreamSource streamSource = controller.getCsvStreamSource();
        assertEquals("work_shares_by_agg_lc_class_report_Scenario_name_01_02_2019_03_04.csv",
            streamSource.getSource().getKey().get());
        assertEquals(fileName, fileNameSupplierCapture.getValue().get());
        Consumer<PipedOutputStream> posConsumer = posConsumerCapture.getValue();
        posConsumer.accept(pos);
        verify(OffsetDateTime.class, widget, streamSourceHandler);
    }

    @Test
    public void testInstantiateWidget() {
        WorkSharesByAggLcClassReportController controller = new WorkSharesByAggLcClassReportController();
        ICommonScenarioReportWidget widget = controller.instantiateWidget();
        assertNotNull(controller.instantiateWidget());
        assertEquals(CommonScenarioReportWidget.class, widget.getClass());
    }
}
