package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.reporting.impl.StreamSource;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolderDto;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDetailDto;
import com.copyright.rup.dist.foreign.domain.filter.RightsholderResultsFilter;
import com.copyright.rup.dist.foreign.service.api.acl.IAclCalculationReportService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioUsageService;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioDetailsByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioDetailsController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioWidget;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.ui.Window;

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
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Verifies {@link AclScenarioController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/28/2022
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({OffsetDateTime.class, StreamSource.class, Windows.class})
public class AclScenarioControllerTest {

    private static final String SCENARIO_UID = "2398769d-8862-42e8-9504-9cbe19376b4b";
    private static final Long RH_ACCOUNT_NUMBER = 1000001863L;
    private static final String RH_NAME = "CANADIAN CERAMIC SOCIETY";
    private static final OffsetDateTime DATE = OffsetDateTime.of(2019, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));

    private AclScenarioController controller;
    private IAclScenarioUsageService aclScenarioUsageService;
    private AclScenario scenario;
    private IStreamSourceHandler streamSourceHandler;
    private IAclCalculationReportService aclCalculationReportService;
    private IAclScenarioDetailsByRightsholderController scenarioDetailsByRightsholderController;
    private IAclScenarioDetailsController scenarioDetailsController;

    @Before
    public void setUp() {
        scenario = buildAclScenario();
        controller = new AclScenarioController();
        controller.setScenario(scenario);
        streamSourceHandler = createMock(IStreamSourceHandler.class);
        aclCalculationReportService = createMock(IAclCalculationReportService.class);
        aclScenarioUsageService = createMock(IAclScenarioUsageService.class);
        scenarioDetailsByRightsholderController = createMock(IAclScenarioDetailsByRightsholderController.class);
        scenarioDetailsController = createMock(IAclScenarioDetailsController.class);
        Whitebox.setInternalState(controller, streamSourceHandler);
        Whitebox.setInternalState(controller, aclCalculationReportService);
        Whitebox.setInternalState(controller, aclScenarioUsageService);
        Whitebox.setInternalState(controller, scenarioDetailsByRightsholderController);
        Whitebox.setInternalState(controller, scenarioDetailsController);
    }

    @Test
    public void testGetScenario() {
        assertEquals(scenario, controller.getScenario());
    }

    @Test
    public void testSetScenario() {
        controller.setScenario(scenario);
        assertSame(scenario, controller.getScenario());
    }

    @Test
    public void testGetAclRightsholderTotalsHoldersByScenarioId() {
        List<AclRightsholderTotalsHolder> holders = List.of(new AclRightsholderTotalsHolder());
        expect(aclScenarioUsageService.getAclRightsholderTotalsHoldersByScenarioId(scenario.getId()))
            .andReturn(holders).once();
        replay(aclScenarioUsageService);
        List<AclRightsholderTotalsHolder> result = controller.getAclRightsholderTotalsHolders();
        assertNotNull(result);
        assertSame(holders, result);
        verify(aclScenarioUsageService);
    }

    @Test
    public void testInstantiateWidget() {
        IAclScenarioWidget widget = controller.instantiateWidget();
        assertNotNull(widget);
        assertEquals(AclScenarioWidget.class, widget.getClass());
    }

    @Test
    public void testGetRightsholderDetailsResults() {
        RightsholderResultsFilter filter = new RightsholderResultsFilter();
        List<AclScenarioDetailDto> scenarioDetails = new ArrayList<>();
        expect(aclScenarioUsageService.getRightsholderDetailsResults(filter)).andReturn(scenarioDetails).once();
        replay(aclScenarioUsageService);
        assertSame(scenarioDetails, controller.getRightsholderDetailsResults(filter));
        verify(aclScenarioUsageService);
    }

    @Test
    public void testGetRightsholderTitleResults() {
        List<AclRightsholderTotalsHolderDto> holderDtos = List.of(buildAclRightsholderTotalsHolderDto());
        RightsholderResultsFilter filter = buildRightsholderResultsFilter();
        expect(aclScenarioUsageService.getRightsholderTitleResults(filter)).andReturn(holderDtos).once();
        replay(aclScenarioUsageService);
        assertSame(holderDtos, controller.getRightsholderTitleResults(filter));
        verify(aclScenarioUsageService);
    }

    @Test
    public void testGetRightsholderAggregateLicenseeClassResults() {
        List<AclRightsholderTotalsHolderDto> holderDtos = List.of(buildAclRightsholderTotalsHolderDto());
        RightsholderResultsFilter filter = buildRightsholderResultsFilter();
        expect(aclScenarioUsageService.getRightsholderAggLcClassResults(filter)).andReturn(holderDtos).once();
        replay(aclScenarioUsageService);
        assertSame(holderDtos, controller.getRightsholderAggLcClassResults(filter));
        verify(aclScenarioUsageService);
    }

    @Test
    public void testGetExportAclScenarioRightsholderTotalsStreamSource() {
        mockStatic(OffsetDateTime.class);
        Capture<Supplier<String>> fileNameSupplierCapture = newCapture();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = newCapture();
        String fileName = scenario.getName() + "_";
        Supplier<String> fileNameSupplier = () -> fileName;
        Supplier<InputStream> inputStreamSupplier =
                () -> IOUtils.toInputStream(StringUtils.EMPTY, StandardCharsets.UTF_8);
        PipedOutputStream pos = new PipedOutputStream();
        expect(OffsetDateTime.now()).andReturn(DATE).once();
        expect(streamSourceHandler.getCsvStreamSource(capture(fileNameSupplierCapture), capture(posConsumerCapture)))
            .andReturn(new StreamSource(fileNameSupplier, "csv", inputStreamSupplier)).once();
        aclCalculationReportService.writeAclScenarioRightsholderTotalsCsvReport(scenario.getId(), pos);
        expectLastCall().once();
        replay(OffsetDateTime.class, streamSourceHandler, aclCalculationReportService);
        IStreamSource streamSource = controller.getExportAclScenarioRightsholderTotalsStreamSource();
        assertEquals("Scenario_name_01_02_2019_03_04.csv", streamSource.getSource().getKey().get());
        assertEquals(fileName, fileNameSupplierCapture.getValue().get());
        Consumer<PipedOutputStream> posConsumer = posConsumerCapture.getValue();
        posConsumer.accept(pos);
        verify(OffsetDateTime.class, streamSourceHandler, aclCalculationReportService);
    }

    @Test
    public void testOnRightsholderAccountNumberClicked() {
        scenarioDetailsByRightsholderController.showWidget(
            1000009767L, "American Chemical Society", buildAclScenario());
        expectLastCall().once();
        replay(scenarioDetailsByRightsholderController);
        controller.onRightsholderAccountNumberClicked(1000009767L, "American Chemical Society");
        verify(scenarioDetailsByRightsholderController);
    }

    @Test
    public void testOnViewDetailsClicked() {
        scenarioDetailsController.showWidget(buildAclScenario());
        expectLastCall().once();
        replay(scenarioDetailsController);
        controller.onViewDetailsClicked();
        verify(scenarioDetailsController);
    }

    @Test
    public void testOpenAclScenarioDrillDownTitlesWindow() {
        mockStatic(Windows.class);
        RightsholderResultsFilter filter = buildRightsholderResultsFilter();
        expect(aclScenarioUsageService.getRightsholderTitleResults(filter)).andReturn(List.of()).once();
        Capture<Window> windowCapture = newCapture();
        Windows.showModalWindow(capture(windowCapture));
        expectLastCall().once();
        replay(aclScenarioUsageService, Windows.class);
        controller.openAclScenarioDrillDownWindow(filter);
        verify(aclScenarioUsageService, Windows.class);
        Window window = windowCapture.getValue();
        assertNotNull(window);
        assertThat(window, instanceOf(AclScenarioDrillDownTitlesWindow.class));
    }

    @Test
    public void testOpenAclScenarioDrillDownAggLcClassesWindow() {
        mockStatic(Windows.class);
        RightsholderResultsFilter filter = buildRightsholderResultsFilter();
        filter.setWrWrkInst(123456789L);
        expect(aclScenarioUsageService.getRightsholderAggLcClassResults(filter)).andReturn(List.of()).once();
        Capture<Window> windowCapture = newCapture();
        Windows.showModalWindow(capture(windowCapture));
        expectLastCall().once();
        replay(aclScenarioUsageService, Windows.class);
        controller.openAclScenarioDrillDownWindow(filter);
        verify(aclScenarioUsageService, Windows.class);
        Window window = windowCapture.getValue();
        assertNotNull(window);
        assertThat(window, instanceOf(AclScenarioDrillDownAggLcClassesWindow.class));
    }

    @Test
    public void testOpenAclScenarioDrillDownUsageDetailsWindow() {
        mockStatic(Windows.class);
        RightsholderResultsFilter filter = buildRightsholderResultsFilter();
        filter.setWrWrkInst(123456789L);
        filter.setAggregateLicenseeClassId(1);
        expect(aclScenarioUsageService.getRightsholderDetailsResults(filter)).andReturn(List.of()).once();
        Capture<Window> windowCapture = newCapture();
        Windows.showModalWindow(capture(windowCapture));
        expectLastCall().once();
        replay(aclScenarioUsageService, Windows.class);
        controller.openAclScenarioDrillDownWindow(filter);
        verify(aclScenarioUsageService, Windows.class);
        Window window = windowCapture.getValue();
        assertNotNull(window);
        assertThat(window, instanceOf(AclScenarioDrillDownUsageDetailsWindow.class));
    }

    private AclScenario buildAclScenario() {
        AclScenario aclScenario = new AclScenario();
        aclScenario.setId(SCENARIO_UID);
        aclScenario.setName("Scenario name");
        return aclScenario;
    }

    private AclRightsholderTotalsHolderDto buildAclRightsholderTotalsHolderDto() {
        AclRightsholderTotalsHolderDto holder = new AclRightsholderTotalsHolderDto();
        holder.getRightsholder().setAccountNumber(RH_ACCOUNT_NUMBER);
        holder.getRightsholder().setName(RH_NAME);
        holder.setGrossTotalPrint(new BigDecimal("2000.0000000000"));
        holder.setNetTotalPrint(new BigDecimal("8300.0000000000"));
        holder.setGrossTotalDigital(new BigDecimal("1000.0000000000"));
        holder.setNetTotalDigital(new BigDecimal("2000.0000000000"));
        holder.setWrWrkInst(127778306L);
        holder.setSystemTitle("Adaptations");
        holder.setGrossTotal(new BigDecimal("3000.0000000000"));
        holder.setNetTotal(new BigDecimal("10300.0000000000"));
        return holder;
    }

    private RightsholderResultsFilter buildRightsholderResultsFilter() {
        RightsholderResultsFilter filter = new RightsholderResultsFilter();
        filter.setScenarioId(SCENARIO_UID);
        filter.setRhAccountNumber(RH_ACCOUNT_NUMBER);
        filter.setRhName(RH_NAME);
        return filter;
    }
}
