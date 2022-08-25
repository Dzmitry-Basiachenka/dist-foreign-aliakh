package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isNull;
import static org.easymock.EasyMock.newCapture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.reporting.impl.StreamSource;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDetailDto;
import com.copyright.rup.dist.foreign.domain.AclScenarioDto;
import com.copyright.rup.dist.foreign.service.api.acl.IAclCalculationReportService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioUsageService;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioWidget;
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
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Collections;
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
@PrepareForTest({OffsetDateTime.class, StreamSource.class})
public class AclScenarioControllerTest {

    private static final String SCENARIO_UID = "2398769d-8862-42e8-9504-9cbe19376b4b";
    private static final Long ACCOUNT_NUMBER = 1000001863L;
    private static final String TITLE = "Langmuir";
    private static final Integer AGG_LIC_CLASS_ID = 56;
    private static final OffsetDateTime DATE = OffsetDateTime.of(2019, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));

    private AclScenarioController controller;
    private IAclScenarioUsageService aclScenarioUsageService;
    private AclScenario scenario;
    private IStreamSourceHandler streamSourceHandler;
    private IAclCalculationReportService aclCalculationReportService;

    @Before
    public void setUp() {
        scenario = buildAclScenario();
        controller = new AclScenarioController();
        controller.setScenario(scenario);
        streamSourceHandler = createMock(IStreamSourceHandler.class);
        aclCalculationReportService = createMock(IAclCalculationReportService.class);
        aclScenarioUsageService = createMock(IAclScenarioUsageService.class);
        Whitebox.setInternalState(controller, streamSourceHandler);
        Whitebox.setInternalState(controller, aclCalculationReportService);
        Whitebox.setInternalState(controller, aclScenarioUsageService);
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
    public void testPerformSearch() {
        IAclScenarioWidget widget = createMock(IAclScenarioWidget.class);
        Whitebox.setInternalState(controller, IWidget.class, widget);
        widget.applySearch();
        expectLastCall().once();
        replay(widget);
        controller.performSearch();
        verify(widget);
    }

    @Test
    public void testGetAclScenarioWithAmountsAndLastAction() {
        AclScenarioDto scenarioDto = new AclScenarioDto();
        scenarioDto.setId(SCENARIO_UID);
        expect(aclScenarioUsageService.getAclScenarioWithAmountsAndLastAction(scenario.getId())).andReturn(scenarioDto)
            .once();
        replay(aclScenarioUsageService);
        assertSame(scenarioDto, controller.getAclScenarioWithAmountsAndLastAction());
        verify(aclScenarioUsageService);
    }

    @Test
    public void testLoadBeans() {
        AclScenarioDto scenarioDto = new AclScenarioDto();
        scenarioDto.setId(SCENARIO_UID);
        Capture<Pageable> pageableCapture = newCapture();
        expect(aclScenarioUsageService.getAclRightsholderTotalsHoldersByScenarioId(eq(scenario.getId()), anyString(),
            capture(pageableCapture), isNull())).andReturn(Collections.emptyList()).once();
        expect(aclScenarioUsageService.getAclScenarioWithAmountsAndLastAction(scenario.getId())).andReturn(scenarioDto)
            .once();
        Capture<Supplier<String>> fileNameSupplierCapture = newCapture();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = newCapture();
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource()).andReturn(new SimpleImmutableEntry(createMock(Supplier.class),
            createMock(Supplier.class))).once();
        expect(streamSourceHandler.getCsvStreamSource(capture(fileNameSupplierCapture), capture(posConsumerCapture)))
            .andReturn(streamSource).once();
        replay(aclScenarioUsageService, streamSourceHandler, streamSource);
        controller.initWidget();
        List<AclRightsholderTotalsHolder> result = controller.loadBeans(10, 150, null);
        Pageable pageable = pageableCapture.getValue();
        assertEquals(10, pageable.getOffset());
        assertEquals(150, pageable.getLimit());
        assertNotNull(result);
        assertEquals(0, result.size());
        assertNotNull(fileNameSupplierCapture.getValue());
        assertNotNull(posConsumerCapture.getValue());
        verify(aclScenarioUsageService, streamSourceHandler, streamSource);
    }

    @Test
    public void testGetSize() {
        AclScenarioDto scenarioDto = new AclScenarioDto();
        scenarioDto.setId(SCENARIO_UID);
        expect(aclScenarioUsageService.getAclRightsholderTotalsHolderCountByScenarioId(scenario.getId(),
            StringUtils.EMPTY)).andReturn(1).once();
        expect(controller.getAclScenarioWithAmountsAndLastAction()).andReturn(scenarioDto).once();
        Capture<Supplier<String>> fileNameSupplierCapture = newCapture();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = newCapture();
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource()).andReturn(new SimpleImmutableEntry(createMock(Supplier.class),
            createMock(Supplier.class))).once();
        expect(streamSourceHandler.getCsvStreamSource(capture(fileNameSupplierCapture), capture(posConsumerCapture)))
            .andReturn(streamSource).once();
        replay(aclScenarioUsageService, streamSourceHandler, streamSource);
        controller.initWidget();
        assertEquals(1, controller.getSize());
        assertNotNull(fileNameSupplierCapture.getValue());
        assertNotNull(posConsumerCapture.getValue());
        verify(aclScenarioUsageService, streamSourceHandler, streamSource);
    }

    @Test
    public void testInstantiateWidget() {
        IAclScenarioWidget widget = controller.instantiateWidget();
        assertNotNull(widget);
        assertEquals(AclScenarioWidget.class, widget.getClass());
    }

    @Test
    public void testGetExportAclScenarioDetailsStreamSource() {
        mockStatic(OffsetDateTime.class);
        Capture<Supplier<String>> fileNameSupplierCapture = newCapture();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = newCapture();
        String fileName = scenario.getName() + "_Details_";
        Supplier<String> fileNameSupplier = () -> fileName;
        Supplier<InputStream> isSupplier = () -> IOUtils.toInputStream(StringUtils.EMPTY, StandardCharsets.UTF_8);
        PipedOutputStream pos = new PipedOutputStream();
        expect(OffsetDateTime.now()).andReturn(DATE).once();
        expect(streamSourceHandler.getCsvStreamSource(capture(fileNameSupplierCapture), capture(posConsumerCapture)))
            .andReturn(new StreamSource(fileNameSupplier, "csv", isSupplier)).once();
        aclCalculationReportService.writeAclScenarioDetailsCsvReport(scenario.getId(), pos);
        expectLastCall().once();
        replay(OffsetDateTime.class, streamSourceHandler, aclCalculationReportService);
        IStreamSource streamSource = controller.getExportAclScenarioDetailsStreamSource();
        assertEquals("Scenario_name_Details_01_02_2019_03_04.csv", streamSource.getSource().getKey().get());
        assertEquals(fileName, fileNameSupplierCapture.getValue().get());
        Consumer<PipedOutputStream> posConsumer = posConsumerCapture.getValue();
        posConsumer.accept(pos);
        verify(OffsetDateTime.class, streamSourceHandler, aclCalculationReportService);
    }

    @Test
    public void testGetByScenarioIdAndRhAccountNumberAndTitleAndAggLicClass() {
        List<AclScenarioDetailDto> scenarioDetails = new ArrayList<>();
        expect(aclScenarioUsageService.getByScenarioIdAndRhAccountNumberAndTitleAndAggLicClass(
            SCENARIO_UID, ACCOUNT_NUMBER, TITLE, AGG_LIC_CLASS_ID)).andReturn(scenarioDetails).once();
        replay(aclScenarioUsageService);
        assertSame(scenarioDetails, controller.getByScenarioIdAndRhAccountNumberAndTitleAndAggLicClass(
            SCENARIO_UID, ACCOUNT_NUMBER, TITLE, AGG_LIC_CLASS_ID));
        verify(aclScenarioUsageService);
    }

    @Test
    public void testGetRightsholderTitleResults() {
        // TODO {dbasiachenka} implement
    }

    private AclScenario buildAclScenario() {
        AclScenario aclScenario = new AclScenario();
        aclScenario.setId(SCENARIO_UID);
        aclScenario.setName("Scenario name");
        return aclScenario;
    }
}
