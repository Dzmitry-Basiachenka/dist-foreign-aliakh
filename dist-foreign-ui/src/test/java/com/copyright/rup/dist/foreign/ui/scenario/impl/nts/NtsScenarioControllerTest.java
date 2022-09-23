package com.copyright.rup.dist.foreign.ui.scenario.impl.nts;

import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.reporting.impl.StreamSource;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.ScenarioService;
import com.copyright.rup.dist.foreign.service.impl.UsageService;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasScenarioWidget;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.api.IWidget;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.InputStream;
import java.io.PipedOutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Verifies {@link NtsScenarioController}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 04/07/17
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, OffsetDateTime.class, StreamSource.class, ForeignSecurityUtils.class})
public class NtsScenarioControllerTest {

    private static final OffsetDateTime NOW = OffsetDateTime.of(2019, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
    private static final String SCENARIO_ID = RupPersistUtils.generateUuid();

    private NtsScenarioController controller;
    private IUsageService usageService;
    private IScenarioService scenarioService;
    private IReportService reportService;
    private IStreamSourceHandler streamSourceHandler;
    private Scenario scenario;

    @Before
    public void setUp() {
        scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setDescription("Description");
        scenario.setNetTotal(new BigDecimal("10000.00"));
        scenario.setGrossTotal(new BigDecimal("20000.00"));
        scenario.setCreateUser("User@copyright.com");
        controller = new NtsScenarioController();
        controller.setScenario(buildScenario());
        usageService = createMock(UsageService.class);
        scenarioService = createMock(ScenarioService.class);
        reportService = createMock(IReportService.class);
        streamSourceHandler = createMock(IStreamSourceHandler.class);
        mockStatic(ForeignSecurityUtils.class);
        Whitebox.setInternalState(controller, usageService);
        Whitebox.setInternalState(controller, scenarioService);
        Whitebox.setInternalState(controller, reportService);
        Whitebox.setInternalState(controller, streamSourceHandler);
    }

    @Test
    public void testLoadBeans() {
        Capture<Pageable> pageableCapture = new Capture<>();
        expect(usageService.getRightsholderTotalsHoldersByScenario(eq(scenario), anyString(),
            capture(pageableCapture), isNull())).andReturn(Collections.emptyList()).once();
        expect(scenarioService.getScenarioWithAmountsAndLastAction(scenario)).andReturn(scenario).once();
        Capture<Supplier<String>> fileNameSupplierCapture = new Capture<>();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = new Capture<>();
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource()).andReturn(new SimpleImmutableEntry(createMock(Supplier.class),
            createMock(Supplier.class))).times(2);
        expect(streamSourceHandler.getCsvStreamSource(capture(fileNameSupplierCapture), capture(posConsumerCapture)))
            .andReturn(streamSource).times(2);
        expect(ForeignSecurityUtils.hasExcludeFromScenarioPermission()).andReturn(true).once();
        replay(usageService, scenarioService, streamSourceHandler, streamSource, ForeignSecurityUtils.class);
        controller.initWidget();
        List<RightsholderTotalsHolder> result = controller.loadBeans(10, 150, null);
        Pageable pageable = pageableCapture.getValue();
        assertEquals(10, pageable.getOffset());
        assertEquals(150, pageable.getLimit());
        assertNotNull(result);
        assertEquals(0, result.size());
        assertNotNull(fileNameSupplierCapture.getValue());
        assertNotNull(posConsumerCapture.getValue());
        verify(usageService, scenarioService, streamSourceHandler, streamSource, ForeignSecurityUtils.class);
    }

    @Test
    public void testGetSize() {
        expect(usageService.getRightsholderTotalsHolderCountByScenario(scenario, StringUtils.EMPTY)).andReturn(1)
            .once();
        expect(controller.getScenarioWithAmountsAndLastAction()).andReturn(scenario).once();
        Capture<Supplier<String>> fileNameSupplierCapture = new Capture<>();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = new Capture<>();
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource()).andReturn(new SimpleImmutableEntry(createMock(Supplier.class),
            createMock(Supplier.class))).times(2);
        expect(streamSourceHandler.getCsvStreamSource(capture(fileNameSupplierCapture), capture(posConsumerCapture)))
            .andReturn(streamSource).times(2);
        expect(ForeignSecurityUtils.hasExcludeFromScenarioPermission()).andReturn(true).once();
        replay(usageService, scenarioService, streamSourceHandler, streamSource, ForeignSecurityUtils.class);
        controller.initWidget();
        assertEquals(1, controller.getSize());
        assertNotNull(fileNameSupplierCapture.getValue());
        assertNotNull(posConsumerCapture.getValue());
        verify(usageService, scenarioService, streamSourceHandler, streamSource, ForeignSecurityUtils.class);
    }

    @Test
    public void testInstantiateWidget() {
        assertNotNull(controller.instantiateWidget());
    }

    @Test
    public void testApplySearch() {
        IFasScenarioWidget widget = createMock(IFasScenarioWidget.class);
        Whitebox.setInternalState(controller, IWidget.class, widget);
        widget.applySearch();
        expectLastCall().once();
        replay(widget, scenarioService);
        controller.performSearch();
        verify(widget, scenarioService);
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
        Capture<Supplier<String>> fileNameSupplierCapture = new Capture<>();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = new Capture<>();
        String fileName = scenario.getName() + "_Details_";
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
        Capture<Supplier<String>> fileNameSupplierCapture = new Capture<>();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = new Capture<>();
        String fileName = scenario.getName() + "_";
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

    private Scenario buildScenario() {
        scenario.setId(SCENARIO_ID);
        scenario.setName("Scenario name");
        return scenario;
    }
}
