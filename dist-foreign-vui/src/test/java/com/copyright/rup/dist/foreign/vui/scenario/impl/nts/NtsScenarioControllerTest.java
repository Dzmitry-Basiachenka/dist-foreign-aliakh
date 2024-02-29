package com.copyright.rup.dist.foreign.vui.scenario.impl.nts;

import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isNull;
import static org.easymock.EasyMock.newCapture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.reporting.impl.StreamSource;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.UsageService;
import com.copyright.rup.dist.foreign.vui.scenario.api.nts.INtsExcludeRightsholderController;
import com.copyright.rup.dist.foreign.vui.scenario.api.nts.INtsScenarioWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IWidget;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.InputStream;
import java.io.PipedOutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Verifies {@link NtsScenarioController}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 04/07/2017
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({OffsetDateTime.class, StreamSource.class, Windows.class})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class NtsScenarioControllerTest {

    private static final OffsetDateTime NOW = OffsetDateTime.of(2019, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
    private static final String SCENARIO_ID = "669eeb53-3191-4649-90c5-001d385d2549";

    private NtsScenarioController controller;
    private IUsageService usageService;
    private IReportService reportService;
    private IStreamSourceHandler streamSourceHandler;
    private INtsScenarioWidget scenarioWidget;
    private INtsExcludeRightsholderController excludeController;
    private Scenario scenario;

    @Before
    public void setUp() {
        buildScenario();
        controller = new NtsScenarioController();
        controller.setScenario(scenario);
        usageService = createMock(UsageService.class);
        reportService = createMock(IReportService.class);
        streamSourceHandler = createMock(IStreamSourceHandler.class);
        scenarioWidget = createMock(INtsScenarioWidget.class);
        excludeController = createMock(INtsExcludeRightsholderController.class);
        Whitebox.setInternalState(controller, usageService);
        Whitebox.setInternalState(controller, reportService);
        Whitebox.setInternalState(controller, streamSourceHandler);
        Whitebox.setInternalState(controller, IWidget.class, scenarioWidget);
        Whitebox.setInternalState(controller, excludeController);
    }

    @Test
    public void testLoadBeans() {
        Capture<Pageable> pageableCapture = newCapture();
        expect(usageService.getRightsholderTotalsHoldersByScenario(eq(scenario), anyString(),
            capture(pageableCapture), isNull())).andReturn(List.of()).once();
        expect(scenarioWidget.getSearchValue()).andReturn(StringUtils.EMPTY).once();
        replay(usageService, scenarioWidget);
        List<RightsholderTotalsHolder> result = controller.loadBeans(10, 150, null);
        Pageable pageable = pageableCapture.getValue();
        assertEquals(10, pageable.getOffset());
        assertEquals(150, pageable.getLimit());
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(usageService, scenarioWidget);
    }

    @Test
    public void testGetSize() {
        expect(usageService.getRightsholderTotalsHolderCountByScenario(scenario, StringUtils.EMPTY)).andReturn(1)
            .once();
        expect(scenarioWidget.getSearchValue()).andReturn(StringUtils.EMPTY).once();
        replay(usageService, scenarioWidget);
        assertEquals(1, controller.getSize());
        verify(usageService, scenarioWidget);
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(controller.instantiateWidget(), instanceOf(NtsScenarioWidget.class));
    }

    @Test
    public void testPerformSearch() {
        scenarioWidget.applySearch();
        expectLastCall().once();
        replay(scenarioWidget);
        controller.performSearch();
        verify(scenarioWidget);
    }

    @Test
    public void testGetScenario() {
        assertEquals(buildScenario(), controller.getScenario());
    }

    @Test
    public void testSetScenario() {
        controller.setScenario(scenario);
        assertSame(scenario, controller.getScenario());
    }

    @Test
    public void testGetExportScenarioUsagesStreamSource() {
        mockStatic(OffsetDateTime.class);
        Capture<Supplier<String>> fileNameSupplierCapture = newCapture();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = newCapture();
        var fileName = scenario.getName() + "_Details_";
        Supplier<String> fileNameSupplier = () -> fileName;
        Supplier<InputStream> inputStreamSupplier =
                () -> IOUtils.toInputStream(StringUtils.EMPTY, StandardCharsets.UTF_8);
        PipedOutputStream pos = new PipedOutputStream();
        expect(OffsetDateTime.now()).andReturn(NOW).once();
        expect(streamSourceHandler.getCsvStreamSource(capture(fileNameSupplierCapture), capture(posConsumerCapture)))
            .andReturn(new StreamSource(fileNameSupplier, "csv", inputStreamSupplier)).once();
        reportService.writeNtsScenarioUsagesCsvReport(scenario, pos);
        expectLastCall().once();
        replay(OffsetDateTime.class, streamSourceHandler, reportService);
        IStreamSource streamSource = controller.getExportScenarioUsagesStreamSource();
        assertEquals("Scenario_name_Details_01_02_2019_03_04.csv", streamSource.getSource().getKey().get());
        assertEquals(fileName, fileNameSupplierCapture.getValue().get());
        Consumer<PipedOutputStream> posConsumer = posConsumerCapture.getValue();
        posConsumer.accept(pos);
        verify(OffsetDateTime.class, streamSourceHandler, reportService);
    }

    @Test
    public void testGetExportScenarioRightsholderTotalsStreamSource() {
        mockStatic(OffsetDateTime.class);
        Capture<Supplier<String>> fileNameSupplierCapture = newCapture();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = newCapture();
        var fileName = scenario.getName() + "_";
        Supplier<String> fileNameSupplier = () -> fileName;
        Supplier<InputStream> inputStreamSupplier =
                () -> IOUtils.toInputStream(StringUtils.EMPTY, StandardCharsets.UTF_8);
        PipedOutputStream pos = new PipedOutputStream();
        expect(OffsetDateTime.now()).andReturn(NOW).once();
        expect(streamSourceHandler.getCsvStreamSource(capture(fileNameSupplierCapture), capture(posConsumerCapture)))
            .andReturn(new StreamSource(fileNameSupplier, "csv", inputStreamSupplier)).once();
        reportService.writeScenarioRightsholderTotalsCsvReport(scenario, pos);
        expectLastCall().once();
        replay(OffsetDateTime.class, streamSourceHandler, reportService);
        IStreamSource streamSource = controller.getExportScenarioRightsholderTotalsStreamSource();
        assertEquals("Scenario_name_01_02_2019_03_04.csv", streamSource.getSource().getKey().get());
        assertEquals(fileName, fileNameSupplierCapture.getValue().get());
        Consumer<PipedOutputStream> posConsumer = posConsumerCapture.getValue();
        posConsumer.accept(pos);
        verify(OffsetDateTime.class, streamSourceHandler, reportService);
    }

    @Test
    public void testOnExcludeRhButtonClicked() {
        mockStatic(Windows.class);
        var widget = new NtsExcludeRightsholderWidget();
        excludeController.setSelectedScenario(controller.getScenario());
        expectLastCall().once();
        expect(excludeController.initWidget()).andReturn(widget).once();
        Windows.showModalWindow(widget);
        expectLastCall().once();
        replay(excludeController, Windows.class);
        controller.onExcludeRhButtonClicked();
        verify(excludeController, Windows.class);
    }

    private Scenario buildScenario() {
        scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setName("Scenario name");
        scenario.setDescription("Description");
        scenario.setNetTotal(new BigDecimal("10000.00"));
        scenario.setGrossTotal(new BigDecimal("20000.00"));
        scenario.setCreateUser("User@copyright.com");
        return scenario;
    }
}
