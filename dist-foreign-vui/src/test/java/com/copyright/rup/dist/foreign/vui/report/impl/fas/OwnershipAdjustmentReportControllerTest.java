package com.copyright.rup.dist.foreign.vui.report.impl.fas;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.reporting.impl.StreamSource;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.vui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.vui.report.api.ICommonScenarioReportWidget;
import com.copyright.rup.dist.foreign.vui.report.impl.CommonScenarioReportWidget;

import com.google.common.collect.ImmutableSet;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.easymock.Capture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.InputStream;
import java.io.PipedOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
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
@RunWith(PowerMockRunner.class)
@PrepareForTest({OffsetDateTime.class, StreamSource.class})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class OwnershipAdjustmentReportControllerTest {

    @Test
    public void testGetScenarios() {
        OwnershipAdjustmentReportController controller = new OwnershipAdjustmentReportController();
        IScenarioService scenarioService = createMock(IScenarioService.class);
        IProductFamilyProvider productFamilyProvider = createMock(IProductFamilyProvider.class);
        Whitebox.setInternalState(controller, scenarioService);
        Whitebox.setInternalState(controller, productFamilyProvider);
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn("FAS").once();
        List<Scenario> scenarios = List.of();
        expect(scenarioService.getScenarios("FAS")).andReturn(scenarios).once();
        replay(scenarioService, productFamilyProvider);
        assertEquals(scenarios, controller.getScenarios());
        verify(scenarioService, productFamilyProvider);
    }

    @Test
    public void testGetCsvStreamSource() {
        OffsetDateTime now = OffsetDateTime.of(2019, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
        mockStatic(OffsetDateTime.class);
        OwnershipAdjustmentReportController controller = new OwnershipAdjustmentReportController();
        ICommonScenarioReportWidget widget = createMock(ICommonScenarioReportWidget.class);
        IReportService reportService = createMock(IReportService.class);
        IStreamSourceHandler streamSourceHandler = createMock(IStreamSourceHandler.class);
        Whitebox.setInternalState(controller, widget);
        Whitebox.setInternalState(controller, reportService);
        Whitebox.setInternalState(controller, streamSourceHandler);
        Scenario scenario = new Scenario();
        scenario.setId(RupPersistUtils.generateUuid());
        scenario.setName("Scenario name");
        Capture<Supplier<String>> fileNameSupplierCapture = newCapture();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = newCapture();
        String fileName = String.format("ownership_adjustment_report_%s_", scenario.getName());
        Supplier<String> fileNameSupplier = () -> fileName;
        Supplier<InputStream> inputStreamSupplier =
            () -> IOUtils.toInputStream(StringUtils.EMPTY, StandardCharsets.UTF_8);
        PipedOutputStream pos = new PipedOutputStream();
        expect(OffsetDateTime.now()).andReturn(now).once();
        expect(widget.getScenario()).andReturn(scenario).times(2);
        expect(streamSourceHandler.getCsvStreamSource(capture(fileNameSupplierCapture), capture(posConsumerCapture)))
            .andReturn(new StreamSource(fileNameSupplier, "csv", inputStreamSupplier)).once();
        reportService.writeOwnershipAdjustmentCsvReport(scenario.getId(),
            ImmutableSet.of(RightsholderDiscrepancyStatusEnum.DRAFT, RightsholderDiscrepancyStatusEnum.APPROVED), pos);
        expectLastCall().once();
        replay(OffsetDateTime.class, widget, streamSourceHandler, reportService);
        IStreamSource streamSource = controller.getCsvStreamSource();
        assertEquals("ownership_adjustment_report_Scenario_name_01_02_2019_03_04.csv",
            streamSource.getSource().getKey().get());
        assertEquals(fileName, fileNameSupplierCapture.getValue().get());
        Consumer<PipedOutputStream> posConsumer = posConsumerCapture.getValue();
        posConsumer.accept(pos);
        verify(OffsetDateTime.class, widget, streamSourceHandler, reportService);
    }

    @Test
    public void testInstantiateWidget() {
        OwnershipAdjustmentReportController controller = new OwnershipAdjustmentReportController();
        ICommonScenarioReportWidget widget = controller.instantiateWidget();
        assertNotNull(controller.instantiateWidget());
        assertEquals(CommonScenarioReportWidget.class, widget.getClass());
    }
}
