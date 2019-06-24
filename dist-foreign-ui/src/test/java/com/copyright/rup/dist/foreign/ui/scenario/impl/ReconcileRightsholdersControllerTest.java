package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isNull;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.same;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.reporting.impl.StreamSource;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IRightsholderDiscrepancyService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.ui.scenario.api.IReconcileRightsholdersController;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.io.InputStream;
import java.io.PipedOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Verifies {@link ReconcileRightsholdersController}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/30/18
 *
 * @author Ihar Suvorau
 */
public class ReconcileRightsholdersControllerTest {

    private IReconcileRightsholdersController controller;
    private IScenarioService scenarioService;
    private IRightsholderDiscrepancyService rightsholderDiscrepancyService;
    private IReportService reportService;
    private IStreamSourceHandler streamSourceHandler;
    private Scenario scenario;

    @Before
    public void setUp() {
        controller = new ReconcileRightsholdersController();
        scenario = new Scenario();
        scenario.setId(RupPersistUtils.generateUuid());
        scenario.setName("Test scenario name");
        controller.setScenario(scenario);
        scenarioService = createMock(IScenarioService.class);
        rightsholderDiscrepancyService = createMock(IRightsholderDiscrepancyService.class);
        reportService = createMock(IReportService.class);
        streamSourceHandler = createMock(IStreamSourceHandler.class);
        Whitebox.setInternalState(controller, scenarioService);
        Whitebox.setInternalState(controller, rightsholderDiscrepancyService);
        Whitebox.setInternalState(controller, reportService);
        Whitebox.setInternalState(controller, streamSourceHandler);
    }

    @Test
    public void testApproveReconciliation() {
        controller.setScenario(scenario);
        scenarioService.approveOwnershipChanges(scenario);
        expectLastCall().once();
        replay(scenarioService);
        controller.approveReconciliation();
        verify(scenarioService);
    }

    @Test
    public void testGetBeansCount() {
        expect(rightsholderDiscrepancyService.getCountByScenarioIdAndStatus(scenario.getId(),
            RightsholderDiscrepancyStatusEnum.DRAFT)).andReturn(5).once();
        replay(rightsholderDiscrepancyService);
        assertEquals(5, controller.getBeansCount(), 0);
        verify(rightsholderDiscrepancyService);
    }

    @Test
    public void testGetProhibitedAccountNumbers() {
        List<Long> accountNumbers = Arrays.asList(1000000001L, 1000000002L);
        expect(rightsholderDiscrepancyService.getProhibitedAccountNumbers(scenario.getId()))
            .andReturn(accountNumbers).once();
        replay(rightsholderDiscrepancyService);
        assertSame(accountNumbers, controller.getProhibitedAccountNumbers());
        verify(rightsholderDiscrepancyService);
    }

    @Test
    public void testLoadBeans() {
        Capture<Pageable> pageableCapture = new Capture<>();
        List<RightsholderDiscrepancy> discrepancies = Collections.singletonList(new RightsholderDiscrepancy());
        expect(rightsholderDiscrepancyService.getByScenarioIdAndStatus(same(scenario.getId()),
            same(RightsholderDiscrepancyStatusEnum.DRAFT), capture(pageableCapture), isNull()))
            .andReturn(discrepancies).once();
        replay(rightsholderDiscrepancyService);
        assertSame(discrepancies, controller.loadBeans(0, 10, null));
        Pageable pageable = pageableCapture.getValue();
        assertEquals(0, pageable.getOffset());
        assertEquals(10, pageable.getLimit());
        verify(rightsholderDiscrepancyService);
    }

    @Test
    public void testGetCsvStreamSource() {
        Capture<Supplier<String>> fileNameSupplierCapture = new Capture<>();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = new Capture<>();
        String fileName = String.format("ownership_adjustment_report_%s_", scenario.getName());
        Supplier<String> fileNameSupplier = () -> fileName;
        Supplier<InputStream> isSupplier = () -> IOUtils.toInputStream(StringUtils.EMPTY, StandardCharsets.UTF_8);
        PipedOutputStream pos = new PipedOutputStream();
        expect(streamSourceHandler.getCsvStreamSource(capture(fileNameSupplierCapture), capture(posConsumerCapture)))
            .andReturn(new StreamSource(fileNameSupplier, "csv", isSupplier)).once();
        reportService.writeOwnershipAdjustmentCsvReport(scenario.getId(),
            Collections.singleton(RightsholderDiscrepancyStatusEnum.DRAFT), pos);
        expectLastCall().once();
        replay(streamSourceHandler, reportService);
        IStreamSource streamSource = controller.getCsvStreamSource();
        assertEquals("ownership_adjustment_report_Test_scenario_name_" +
            CommonDateUtils.format(OffsetDateTime.now(), "MM_dd_YYYY_HH_mm") + ".csv", streamSource.getFileName());
        assertEquals(fileName, fileNameSupplierCapture.getValue().get());
        Consumer<PipedOutputStream> posConsumer = posConsumerCapture.getValue();
        posConsumer.accept(pos);
        verify(streamSourceHandler, reportService);
    }
}
