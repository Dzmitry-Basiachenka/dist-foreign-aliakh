package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.reporting.impl.StreamSource;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IRightsholderDiscrepancyService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.ScenarioService;
import com.copyright.rup.dist.foreign.service.impl.UsageService;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.scenario.api.IExcludePayeesController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioWidget;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.api.IWidget;

import com.google.common.collect.Lists;

import org.apache.commons.collections4.CollectionUtils;
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
 * Verifies {@link ScenarioController}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 04/07/17
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ForeignSecurityUtils.class, Windows.class, OffsetDateTime.class, StreamSource.class})
public class ScenarioControllerTest {

    private static final OffsetDateTime NOW = OffsetDateTime.of(2019, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
    private static final String SCENARIO_ID = RupPersistUtils.generateUuid();

    private ScenarioController controller;
    private IUsageService usageService;
    private IScenarioService scenarioService;
    private IReportService reportService;
    private IRightsholderDiscrepancyService rightsholderDiscrepancyService;
    private IStreamSourceHandler streamSourceHandler;
    private IExcludePayeesController excludePayeesController;
    private Scenario scenario;

    @Before
    public void setUp() {
        scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setDescription("Description");
        scenario.setNetTotal(new BigDecimal("10000.00"));
        scenario.setGrossTotal(new BigDecimal("20000.00"));
        scenario.setReportedTotal(new BigDecimal("30000.00"));
        scenario.setCreateUser("User@copyright.com");
        controller = new ScenarioController();
        controller.setScenario(buildScenario());
        usageService = createMock(UsageService.class);
        scenarioService = createMock(ScenarioService.class);
        reportService = createMock(IReportService.class);
        streamSourceHandler = createMock(IStreamSourceHandler.class);
        rightsholderDiscrepancyService = createMock(IRightsholderDiscrepancyService.class);
        excludePayeesController = createMock(IExcludePayeesController.class);
        mockStatic(ForeignSecurityUtils.class);
        Whitebox.setInternalState(controller, usageService);
        Whitebox.setInternalState(controller, scenarioService);
        Whitebox.setInternalState(controller, reportService);
        Whitebox.setInternalState(controller, streamSourceHandler);
        Whitebox.setInternalState(controller, excludePayeesController);
        Whitebox.setInternalState(controller, rightsholderDiscrepancyService);
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
        IScenarioWidget widget = createMock(IScenarioWidget.class);
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
        assertEquals(scenario, controller.getScenario());
    }

    @Test
    public void testGetExportScenarioUsagesStreamSource() {
        mockStatic(OffsetDateTime.class);
        Capture<Supplier<String>> fileNameSupplierCapture = new Capture<>();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = new Capture<>();
        String fileName = scenario.getName() + "_Details_";
        Supplier<String> fileNameSupplier = () -> fileName;
        Supplier<InputStream> isSupplier = () -> IOUtils.toInputStream(StringUtils.EMPTY, StandardCharsets.UTF_8);
        PipedOutputStream pos = new PipedOutputStream();
        expect(OffsetDateTime.now()).andReturn(NOW).once();
        expect(streamSourceHandler.getCsvStreamSource(capture(fileNameSupplierCapture), capture(posConsumerCapture)))
            .andReturn(new StreamSource(fileNameSupplier, "csv", isSupplier)).once();
        reportService.writeScenarioUsagesCsvReport(scenario, pos);
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
        Supplier<InputStream> isSupplier = () -> IOUtils.toInputStream(StringUtils.EMPTY, StandardCharsets.UTF_8);
        PipedOutputStream pos = new PipedOutputStream();
        expect(OffsetDateTime.now()).andReturn(NOW).once();
        expect(streamSourceHandler.getCsvStreamSource(capture(fileNameSupplierCapture), capture(posConsumerCapture)))
            .andReturn(new StreamSource(fileNameSupplier, "csv", isSupplier)).once();
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
    public void testOnExcludeByRroClickedWithDiscrepancies() {
        mockStatic(Windows.class);
        expect(rightsholderDiscrepancyService.getCountByScenarioIdAndStatus(SCENARIO_ID,
            RightsholderDiscrepancyStatusEnum.APPROVED)).andReturn(1).once();
        Windows.showNotificationWindow("Details cannot be excluded after reconciliation");
        expectLastCall().once();
        replay(rightsholderDiscrepancyService, Windows.class);
        controller.onExcludeByRroClicked();
        verify(rightsholderDiscrepancyService, Windows.class);
    }

    @Test
    public void testOnExcludeByRroClickedWithoutDiscrepancies() {
        mockStatic(Windows.class);
        expect(rightsholderDiscrepancyService.getCountByScenarioIdAndStatus(SCENARIO_ID,
            RightsholderDiscrepancyStatusEnum.APPROVED)).andReturn(0).once();
        Windows.showModalWindow(anyObject(ExcludeSourceRroWindow.class));
        expectLastCall().once();
        expect(scenarioService.getSourceRros(SCENARIO_ID)).andReturn(
            Collections.singletonList(buildRightsholder(1000009522L, "Societa Italiana Autori ed Editori (SIAE)")))
            .once();
        replay(rightsholderDiscrepancyService, scenarioService, Windows.class);
        controller.onExcludeByRroClicked();
        verify(rightsholderDiscrepancyService, scenarioService, Windows.class);
    }

    @Test
    public void testOnExcludeByPayeeClickedWithDiscrepancies() {
        mockStatic(Windows.class);
        expect(rightsholderDiscrepancyService.getCountByScenarioIdAndStatus(SCENARIO_ID,
            RightsholderDiscrepancyStatusEnum.APPROVED)).andReturn(1).once();
        Windows.showNotificationWindow("Details cannot be excluded after reconciliation");
        expectLastCall().once();
        replay(rightsholderDiscrepancyService, excludePayeesController, Windows.class);
        controller.onExcludeByPayeeClicked();
        verify(rightsholderDiscrepancyService, excludePayeesController, Windows.class);
    }

    @Test
    public void testOnExcludeByPayeeClickedWithoutDiscrepancies() {
        mockStatic(Windows.class);
        expect(rightsholderDiscrepancyService.getCountByScenarioIdAndStatus(SCENARIO_ID,
            RightsholderDiscrepancyStatusEnum.APPROVED)).andReturn(0).once();
        excludePayeesController.setScenario(scenario);
        expectLastCall().once();
        ExcludePayeesWidget widget = new ExcludePayeesWidget();
        expect(excludePayeesController.initWidget()).andReturn(widget).once();
        Windows.showModalWindow(widget);
        expectLastCall().once();
        replay(rightsholderDiscrepancyService, scenarioService, excludePayeesController, Windows.class);
        controller.onExcludeByPayeeClicked();
        verify(rightsholderDiscrepancyService, scenarioService, excludePayeesController, Windows.class);
    }

    @Test
    public void testGetSourceRros() {
        Whitebox.setInternalState(controller, "scenarioService", scenarioService);
        expect(scenarioService.getSourceRros(SCENARIO_ID)).andReturn(
            Collections.singletonList(buildRightsholder(1000009522L, "Societa Italiana Autori ed Editori (SIAE)")))
            .once();
        replay(scenarioService);
        List<Rightsholder> rros = controller.getSourceRros();
        assertEquals(1, CollectionUtils.size(rros));
        assertEquals(Long.valueOf(1000009522L), rros.get(0).getAccountNumber());
        assertEquals("Societa Italiana Autori ed Editori (SIAE)", rros.get(0).getName());
        verify(scenarioService);
    }

    @Test
    public void testDeleteFromScenario() {
        List<Long> accountNumbers = Lists.newArrayList(1000009522L);
        usageService.deleteFromScenario(SCENARIO_ID, 2000017010L, accountNumbers, "reason");
        expectLastCall().once();
        replay(usageService);
        controller.deleteFromScenario(2000017010L, accountNumbers, "reason");
        verify(usageService);
    }

    @Test
    public void testGetRightsholdersPayeePairs() {
        Whitebox.setInternalState(controller, "scenarioService", scenarioService);
        expect(scenarioService.getRightsholdersByScenarioAndSourceRro(SCENARIO_ID, 1000009522L))
            .andReturn(Collections.singletonList(buildRightsholderPayeePair())).once();
        replay(scenarioService);
        List<RightsholderPayeePair> pairs = controller.getRightsholdersPayeePairs(1000009522L);
        assertEquals(1, CollectionUtils.size(pairs));
        RightsholderPayeePair pair = pairs.get(0);
        assertEquals(Long.valueOf(2000017001L), pair.getPayee().getAccountNumber());
        assertEquals("ICLA, Irish Copyright Licensing Agency", pair.getPayee().getName());
        assertEquals(Long.valueOf(2000017006L), pair.getRightsholder().getAccountNumber());
        assertEquals("CAL, Copyright Agency Limited", pair.getRightsholder().getName());
        verify(scenarioService);
    }

    private Scenario buildScenario() {
        scenario.setId(SCENARIO_ID);
        scenario.setName("Scenario name");
        return scenario;
    }

    private Rightsholder buildRightsholder(Long accountNumber, String name) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setName(name);
        rightsholder.setAccountNumber(accountNumber);
        return rightsholder;
    }

    private RightsholderPayeePair buildRightsholderPayeePair() {
        RightsholderPayeePair pair = new RightsholderPayeePair();
        pair.setPayee(buildRightsholder(2000017001L, "ICLA, Irish Copyright Licensing Agency"));
        pair.setRightsholder(buildRightsholder(2000017006L, "CAL, Copyright Agency Limited"));
        return pair;
    }
}
