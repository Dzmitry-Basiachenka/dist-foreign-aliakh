package com.copyright.rup.dist.foreign.ui.scenario.impl.fas;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isNull;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.same;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.reporting.impl.StreamSource;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.fas.IFasScenarioService;
import com.copyright.rup.dist.foreign.service.api.fas.IRightsholderDiscrepancyService;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IReconcileRightsholdersController;

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
import java.util.Arrays;
import java.util.List;
import java.util.Set;
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
@RunWith(PowerMockRunner.class)
@PrepareForTest({OffsetDateTime.class, StreamSource.class})
public class ReconcileRightsholdersControllerTest {

    private IReconcileRightsholdersController controller;
    private IFasScenarioService fasScenarioService;
    private IRightsholderDiscrepancyService rightsholderDiscrepancyService;
    private IReportService reportService;
    private IStreamSourceHandler streamSourceHandler;
    private Scenario scenario;

    @Before
    public void setUp() {
        controller = new ReconcileRightsholdersController();
        scenario = new Scenario();
        scenario.setId(RupPersistUtils.generateUuid());
        scenario.setName("Scenario name");
        controller.setScenario(scenario);
        fasScenarioService = createMock(IFasScenarioService.class);
        rightsholderDiscrepancyService = createMock(IRightsholderDiscrepancyService.class);
        reportService = createMock(IReportService.class);
        streamSourceHandler = createMock(IStreamSourceHandler.class);
        Whitebox.setInternalState(controller, fasScenarioService);
        Whitebox.setInternalState(controller, rightsholderDiscrepancyService);
        Whitebox.setInternalState(controller, reportService);
        Whitebox.setInternalState(controller, streamSourceHandler);
    }

    @Test
    public void testApproveReconciliation() {
        controller.setScenario(scenario);
        fasScenarioService.approveOwnershipChanges(scenario);
        expectLastCall().once();
        replay(fasScenarioService);
        controller.approveReconciliation();
        verify(fasScenarioService);
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
        Capture<Pageable> pageableCapture = newCapture();
        List<RightsholderDiscrepancy> discrepancies = List.of(new RightsholderDiscrepancy());
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
        OffsetDateTime now = OffsetDateTime.of(2019, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
        mockStatic(OffsetDateTime.class);
        Capture<Supplier<String>> fileNameSupplierCapture = newCapture();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = newCapture();
        String fileName = String.format("ownership_adjustment_report_%s_", scenario.getName());
        Supplier<String> fileNameSupplier = () -> fileName;
        Supplier<InputStream> inputStreamSupplier =
                () -> IOUtils.toInputStream(StringUtils.EMPTY, StandardCharsets.UTF_8);
        PipedOutputStream pos = new PipedOutputStream();
        expect(OffsetDateTime.now()).andReturn(now).once();
        expect(streamSourceHandler.getCsvStreamSource(capture(fileNameSupplierCapture), capture(posConsumerCapture)))
            .andReturn(new StreamSource(fileNameSupplier, "csv", inputStreamSupplier)).once();
        reportService.writeOwnershipAdjustmentCsvReport(scenario.getId(),
            Set.of(RightsholderDiscrepancyStatusEnum.DRAFT), pos);
        expectLastCall().once();
        replay(OffsetDateTime.class, streamSourceHandler, reportService);
        IStreamSource streamSource = controller.getCsvStreamSource();
        assertEquals("ownership_adjustment_report_Scenario_name_01_02_2019_03_04.csv",
            streamSource.getSource().getKey().get());
        assertEquals(fileName, fileNameSupplierCapture.getValue().get());
        Consumer<PipedOutputStream> posConsumer = posConsumerCapture.getValue();
        posConsumer.accept(pos);
        verify(OffsetDateTime.class, streamSourceHandler, reportService);
    }
}
