package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isNull;
import static org.easymock.EasyMock.newCapture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.reporting.impl.StreamSource;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.fas.IFasScenarioService;
import com.copyright.rup.dist.foreign.service.impl.UsageService;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasScenarioWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IWidget;

import org.apache.commons.collections4.CollectionUtils;
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
 * Verifies {@link FasScenarioController}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 04/07/17
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({OffsetDateTime.class, StreamSource.class})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class FasScenarioControllerTest {

    private static final String SCENARIO_ID = "1ab5079f-2b90-4d8d-85d5-fa7ef6df0ed6";
    private static final OffsetDateTime NOW = OffsetDateTime.of(2019, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));

    private FasScenarioController controller;
    private IUsageService usageService;
    private IScenarioService scenarioService;
    private IFasScenarioService fasScenarioService;
    private Scenario scenario;
    private IReportService reportService;
    private IStreamSourceHandler streamSourceHandler;
    private IFasScenarioWidget scenarioWidget;

    @Before
    public void setUp() {
        scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setDescription("Description");
        scenario.setNetTotal(new BigDecimal("10000.00"));
        scenario.setGrossTotal(new BigDecimal("20000.00"));
        scenario.setCreateUser("User@copyright.com");
        controller = new FasScenarioController();
        controller.setScenario(buildScenario());
        usageService = createMock(UsageService.class);
        scenarioService = createMock(IScenarioService.class);
        fasScenarioService = createMock(IFasScenarioService.class);
        reportService = createMock(IReportService.class);
        streamSourceHandler = createMock(IStreamSourceHandler.class);
        scenarioWidget = createMock(IFasScenarioWidget.class);
        Whitebox.setInternalState(controller, usageService);
        Whitebox.setInternalState(controller, scenarioService);
        Whitebox.setInternalState(controller, fasScenarioService);
        Whitebox.setInternalState(controller, reportService);
        Whitebox.setInternalState(controller, streamSourceHandler);
        Whitebox.setInternalState(controller, IWidget.class, scenarioWidget);
    }

    @Test
    public void testLoadBeans() {
        Capture<Pageable> pageableCapture = newCapture();
        expect(usageService.getRightsholderTotalsHoldersByScenario(eq(scenario), eq(StringUtils.EMPTY),
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
        expect(controller.getScenarioWithAmountsAndLastAction()).andReturn(scenario).once();
        replay(usageService, scenarioWidget);
        assertEquals(1, controller.getSize());
        verify(usageService, scenarioWidget);
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(controller.instantiateWidget(), instanceOf(FasScenarioWidget.class));
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
    public void testIsScenarioEmpty() {
        controller.setScenario(scenario);
        expect(usageService.isScenarioEmpty(scenario)).andReturn(true).once();
        replay(usageService);
        assertTrue(controller.isScenarioEmpty());
        verify(usageService);
    }

    @Test
    public void testGetSourceRros() {
        Whitebox.setInternalState(controller, "scenarioService", scenarioService);
        expect(fasScenarioService.getSourceRros(SCENARIO_ID)).andReturn(
            List.of(buildRightsholder(1000009522L, "Societa Italiana Autori ed Editori (SIAE)")))
            .once();
        replay(fasScenarioService);
        List<Rightsholder> rros = controller.getSourceRros();
        assertEquals(1, CollectionUtils.size(rros));
        assertEquals(Long.valueOf(1000009522L), rros.get(0).getAccountNumber());
        assertEquals("Societa Italiana Autori ed Editori (SIAE)", rros.get(0).getName());
        verify(fasScenarioService);
    }

    @Test
    public void testDeleteFromScenario() {
        List<Long> accountNumbers = List.of(1000009522L);
        usageService.deleteFromScenario(SCENARIO_ID, 2000017010L, accountNumbers, "reason");
        expectLastCall().once();
        replay(usageService);
        controller.deleteFromScenario(2000017010L, accountNumbers, "reason");
        verify(usageService);
    }

    @Test
    public void testGetRightsholdersPayeePairs() {
        Whitebox.setInternalState(controller, "scenarioService", scenarioService);
        expect(fasScenarioService.getRightsholdersByScenarioAndSourceRro(SCENARIO_ID, 1000009522L))
            .andReturn(List.of(buildRightsholderPayeePair())).once();
        replay(fasScenarioService);
        List<RightsholderPayeePair> pairs = controller.getRightsholdersPayeePairs(1000009522L);
        assertEquals(1, CollectionUtils.size(pairs));
        RightsholderPayeePair pair = pairs.get(0);
        assertEquals(Long.valueOf(2000017001L), pair.getPayee().getAccountNumber());
        assertEquals("ICLA, Irish Copyright Licensing Agency", pair.getPayee().getName());
        assertEquals(Long.valueOf(2000017006L), pair.getRightsholder().getAccountNumber());
        assertEquals("CAL, Copyright Agency Limited", pair.getRightsholder().getName());
        verify(fasScenarioService);
    }

    @Test
    public void testGetExportScenarioUsagesStreamSource() {
        mockStatic(OffsetDateTime.class);
        Capture<Supplier<String>> fileNameSupplierCapture = newCapture();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = newCapture();
        String fileName = scenario.getName() + "_Details_";
        Supplier<String> fileNameSupplier = () -> fileName;
        Supplier<InputStream> inputStreamSupplier =
            () -> IOUtils.toInputStream(StringUtils.EMPTY, StandardCharsets.UTF_8);
        PipedOutputStream pos = new PipedOutputStream();
        expect(OffsetDateTime.now()).andReturn(NOW).once();
        expect(streamSourceHandler.getCsvStreamSource(capture(fileNameSupplierCapture), capture(posConsumerCapture)))
            .andReturn(new StreamSource(fileNameSupplier, "csv", inputStreamSupplier)).once();
        reportService.writeFasScenarioUsagesCsvReport(scenario, pos);
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

    private Rightsholder buildRightsholder(Long accountNumber, String name) {
        var rightsholder = new Rightsholder();
        rightsholder.setName(name);
        rightsholder.setAccountNumber(accountNumber);
        return rightsholder;
    }

    private RightsholderPayeePair buildRightsholderPayeePair() {
        var pair = new RightsholderPayeePair();
        pair.setPayee(buildRightsholder(2000017001L, "ICLA, Irish Copyright Licensing Agency"));
        pair.setRightsholder(buildRightsholder(2000017006L, "CAL, Copyright Agency Limited"));
        return pair;
    }
}
