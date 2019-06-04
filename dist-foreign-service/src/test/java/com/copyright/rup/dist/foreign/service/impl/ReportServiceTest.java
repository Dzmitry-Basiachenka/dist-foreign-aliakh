package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.test.ReportTestUtils;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IRightsholderDiscrepancyRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

/**
 * Verifies {@link ReportService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 05/16/2018
 *
 * @author Uladzislau Shalamitski
 */
public class ReportServiceTest {

    private static final String PATH_TO_EXPECTED_REPORTS =
        "src/test/resources/com/copyright/rup/dist/foreign/service/impl/csv";
    private static final BigDecimal USAGE_BATCH_GROSS_AMOUNT = BigDecimal.ONE;
    private static final BigDecimal DEFAULT_ESTIMATED_SERVICE_FEE = new BigDecimal("0.18500");

    private final ReportTestUtils reportTestUtils = new ReportTestUtils(PATH_TO_EXPECTED_REPORTS);

    private IReportService reportService;
    private IUsageArchiveRepository usageArchiveRepository;
    private IRightsholderDiscrepancyRepository rightsholderDiscrepancyRepository;
    private IUsageRepository usageRepository;

    @Before
    public void setUp() {
        reportService = new ReportService();
        usageRepository = createMock(IUsageRepository.class);
        usageArchiveRepository = createMock(IUsageArchiveRepository.class);
        rightsholderDiscrepancyRepository = createMock(IRightsholderDiscrepancyRepository.class);
        Whitebox.setInternalState(reportService, usageRepository);
        Whitebox.setInternalState(reportService, usageArchiveRepository);
        Whitebox.setInternalState(reportService, rightsholderDiscrepancyRepository);
        Whitebox.setInternalState(reportService, "defaultEstimatedServiceFee", DEFAULT_ESTIMATED_SERVICE_FEE);
    }

    @Test
    public void testWriteUndistributedLiabilitiesCsvReport() {
        LocalDate paymentDate = LocalDate.now();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        usageRepository
            .writeUndistributedLiabilitiesCsvReport(paymentDate, outputStream, DEFAULT_ESTIMATED_SERVICE_FEE);
        expectLastCall().once();
        replay(usageRepository);
        reportService.writeUndistributedLiabilitiesCsvReport(paymentDate, outputStream);
        verify(usageRepository);
    }

    @Test
    public void testServiceFeeTrueUpCsvReport() {
        LocalDate fromDate = LocalDate.now();
        LocalDate toDate = LocalDate.now();
        LocalDate paymentDateTo = LocalDate.now();
        IUsageService usageService = createMock(IUsageService.class);
        Whitebox.setInternalState(reportService, usageService);
        expect(usageService.getClaAccountNumber()).andReturn(2000017000L).once();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        usageRepository.writeServiceFeeTrueUpCsvReport(fromDate, toDate, paymentDateTo, outputStream, 2000017000L,
            DEFAULT_ESTIMATED_SERVICE_FEE);
        expectLastCall().once();
        replay(usageRepository, usageService);
        reportService.writeServiceFeeTrueUpCsvReport(fromDate, toDate, paymentDateTo, outputStream);
        verify(usageRepository, usageService);
    }

    @Test
    public void testWriteUsageCsvReport() {
        UsageFilter filter = createMock(UsageFilter.class);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        usageRepository.writeUsagesCsvReport(filter, outputStream);
        expectLastCall().once();
        replay(usageRepository);
        reportService.writeUsageCsvReport(filter, outputStream);
        verify(usageRepository);
    }

    @Test
    public void testWriteAuditCsvReport() {
        AuditFilter filter = createMock(AuditFilter.class);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        usageRepository.writeAuditCsvReport(filter, outputStream);
        expectLastCall().once();
        replay(usageRepository);
        reportService.writeAuditCsvReport(filter, outputStream);
        verify(usageRepository);
    }

    @Test
    public void testWriteScenarioUsagesCsvReportStatusSentToLM() {
        Scenario scenario = buildScenario(ScenarioStatusEnum.SENT_TO_LM);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        usageArchiveRepository.writeScenarioUsagesCsvReport(scenario.getId(), outputStream);
        expectLastCall().once();
        replay(usageArchiveRepository);
        reportService.writeScenarioUsagesCsvReport(scenario, outputStream);
        verify(usageArchiveRepository);
    }

    @Test
    public void testWriteScenarioUsagesCsvReportStatusArchived() {
        Scenario scenario = buildScenario(ScenarioStatusEnum.ARCHIVED);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        usageArchiveRepository.writeScenarioUsagesCsvReport(scenario.getId(), outputStream);
        expectLastCall().once();
        replay(usageArchiveRepository);
        reportService.writeScenarioUsagesCsvReport(scenario, outputStream);
        verify(usageArchiveRepository);
    }

    @Test
    public void testWriteScenarioUsagesCsvReportStatusInProgress() {
        Scenario scenario = buildScenario(ScenarioStatusEnum.IN_PROGRESS);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        usageRepository.writeScenarioUsagesCsvReport(scenario.getId(), outputStream);
        expectLastCall().once();
        replay(usageRepository);
        reportService.writeScenarioUsagesCsvReport(scenario, outputStream);
        verify(usageRepository);
    }

    @Test
    public void testWriteScenarioRightsholderTotalsCsvReportStatusArchived() {
        Scenario scenario = buildScenario(ScenarioStatusEnum.ARCHIVED);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        usageArchiveRepository.writeScenarioRightsholderTotalsCsvReport(scenario.getId(), outputStream);
        expectLastCall().once();
        replay(usageArchiveRepository);
        reportService.writeScenarioRightsholderTotalsCsvReport(scenario, outputStream);
        verify(usageArchiveRepository);
    }

    @Test
    public void testWriteScenarioRightsholderTotalsCsvReportStatusInProgress() {
        Scenario scenario = buildScenario(ScenarioStatusEnum.IN_PROGRESS);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        usageRepository.writeScenarioRightsholderTotalsCsvReport(scenario.getId(), outputStream);
        expectLastCall().once();
        replay(usageRepository);
        reportService.writeScenarioRightsholderTotalsCsvReport(scenario, outputStream);
        verify(usageRepository);
    }

    @Test
    public void testWriteScenarioUsagesCsvReportStatusApproved() {
        Scenario scenario = buildScenario(ScenarioStatusEnum.APPROVED);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        usageRepository.writeScenarioUsagesCsvReport(scenario.getId(), outputStream);
        expectLastCall().once();
        replay(usageRepository);
        reportService.writeScenarioUsagesCsvReport(scenario, outputStream);
        verify(usageRepository);
    }

    @Test
    public void testWriteScenarioUsagesCsvReportStatusSubmitted() {
        Scenario scenario = buildScenario(ScenarioStatusEnum.SUBMITTED);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        usageRepository.writeScenarioUsagesCsvReport(scenario.getId(), outputStream);
        expectLastCall().once();
        replay(usageRepository);
        reportService.writeScenarioUsagesCsvReport(scenario, outputStream);
        verify(usageRepository);
    }

    @Test
    public void testWriteFasBatchSummaryCsvReport() {
        ByteArrayOutputStream outputStream = createMock(ByteArrayOutputStream.class);
        usageRepository.writeFasBatchSummaryCsvReport(outputStream);
        expectLastCall().once();
        replay(usageRepository);
        reportService.writeFasBatchSummaryCsvReport(outputStream);
    }

    @Test
    public void testWriteResearchStatusCsvReport() {
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        usageRepository.writeResearchStatusCsvReport(outputStream);
        expectLastCall().once();
        replay(usageRepository);
        reportService.writeResearchStatusCsvReport(outputStream);
        verify(usageRepository);
    }

    @Test
    public void testWriteSummaryMarketCsvReport() {
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        usageRepository.writeSummaryMarketCsvReport(Collections.emptyList(), outputStream);
        expectLastCall().once();
        replay(usageRepository);
        reportService.writeSummaryMarkerCsvReport(Collections.emptyList(), outputStream);
        verify(usageRepository);
    }

    @Test
    public void testWritePreServiceFeeFundBatchesCsvReport() throws IOException {
        PipedOutputStream pos = new PipedOutputStream();
        PipedInputStream pis = new PipedInputStream(pos);
        new ReportService().writePreServiceFeeFundBatchesCsvReport(
            Collections.singletonList(buildUsageBatch()), USAGE_BATCH_GROSS_AMOUNT, pos);
        reportTestUtils.assertCsvReport("batches_nts_withdrawn.csv", pis);
    }

    @Test
    public void testWriteOwnershipAdjustmentCsvReport() {
        String scenarioId = "12b3c369-3084-41ad-92b5-62197660d642";
        Set<RightsholderDiscrepancyStatusEnum> statuses =
            Collections.singleton(RightsholderDiscrepancyStatusEnum.APPROVED);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        rightsholderDiscrepancyRepository.writeOwnershipAdjustmentCsvReport(scenarioId, statuses, outputStream);
        expectLastCall().once();
        replay(rightsholderDiscrepancyRepository);
        reportService.writeOwnershipAdjustmentCsvReport(scenarioId, statuses, outputStream);
        verify(rightsholderDiscrepancyRepository);
    }

    private Scenario buildScenario(ScenarioStatusEnum status) {
        Scenario scenario = new Scenario();
        scenario.setId(RupPersistUtils.generateUuid());
        scenario.setStatus(status);
        return scenario;
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId("2358deb3-caa3-4c4e-85cd-c353fcc8e6b9");
        usageBatch.setName("Copibec 25May18");
        usageBatch.setGrossAmount(USAGE_BATCH_GROSS_AMOUNT);
        return usageBatch;
    }
}
