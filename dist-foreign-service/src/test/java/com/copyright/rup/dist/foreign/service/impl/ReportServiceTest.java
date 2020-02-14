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
import com.copyright.rup.dist.foreign.repository.api.IReportRepository;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.fas.IFasUsageService;

import com.google.common.collect.Sets;

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
    private IReportRepository reportRepository;

    @Before
    public void setUp() {
        reportService = new ReportService();
        reportRepository = createMock(IReportRepository.class);
        Whitebox.setInternalState(reportService, reportRepository);
        Whitebox.setInternalState(reportService, "defaultEstimatedServiceFee", DEFAULT_ESTIMATED_SERVICE_FEE);
    }

    @Test
    public void testWriteUndistributedLiabilitiesCsvReport() {
        LocalDate paymentDate = LocalDate.now();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        reportRepository
            .writeUndistributedLiabilitiesCsvReport(paymentDate, outputStream, DEFAULT_ESTIMATED_SERVICE_FEE,
                Sets.newHashSet("FAS", "FAS2"));
        expectLastCall().once();
        replay(reportRepository);
        reportService.writeUndistributedLiabilitiesCsvReport(paymentDate, outputStream,
            Sets.newHashSet("FAS", "FAS2"));
        verify(reportRepository);
    }

    @Test
    public void testServiceFeeTrueUpCsvReport() {
        LocalDate fromDate = LocalDate.now();
        LocalDate toDate = LocalDate.now();
        LocalDate paymentDateTo = LocalDate.now();
        IFasUsageService fasUsageService = createMock(IFasUsageService.class);
        Whitebox.setInternalState(reportService, fasUsageService);
        expect(fasUsageService.getClaAccountNumber()).andReturn(2000017000L).once();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        reportRepository.writeServiceFeeTrueUpCsvReport(fromDate, toDate, paymentDateTo, outputStream, 2000017000L,
            DEFAULT_ESTIMATED_SERVICE_FEE);
        expectLastCall().once();
        replay(reportRepository, fasUsageService);
        reportService.writeServiceFeeTrueUpCsvReport(fromDate, toDate, paymentDateTo, outputStream);
        verify(reportRepository, fasUsageService);
    }

    @Test
    public void testWriteFasUsageCsvReport() {
        UsageFilter filter = createMock(UsageFilter.class);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        reportRepository.writeFasUsageCsvReport(filter, outputStream);
        expectLastCall().once();
        replay(reportRepository);
        reportService.writeFasUsageCsvReport(filter, outputStream);
        verify(reportRepository);
    }

    @Test
    public void testWriteNtsUsageCsvReport() {
        UsageFilter filter = createMock(UsageFilter.class);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        reportRepository.writeNtsUsageCsvReport(filter, outputStream);
        expectLastCall().once();
        replay(reportRepository);
        reportService.writeNtsUsageCsvReport(filter, outputStream);
        verify(reportRepository);
    }

    @Test
    public void testWriteFasAuditCsvReport() {
        AuditFilter filter = createMock(AuditFilter.class);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        reportRepository.writeAuditFasCsvReport(filter, outputStream);
        expectLastCall().once();
        replay(reportRepository);
        reportService.writeAuditFasCsvReport(filter, outputStream);
        verify(reportRepository);
    }

    @Test
    public void testWriteAuditNtsCsvReport() {
        AuditFilter filter = createMock(AuditFilter.class);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        reportRepository.writeAuditNtsCsvReport(filter, outputStream);
        expectLastCall().once();
        replay(reportRepository);
        reportService.writeAuditNtsCsvReport(filter, outputStream);
        verify(reportRepository);
    }

    @Test
    public void testWriteAaclUsageCsvReport() {
        UsageFilter filter = createMock(UsageFilter.class);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        reportRepository.writeAaclUsagesCsvReport(filter, outputStream);
        expectLastCall().once();
        replay(reportRepository);
        reportService.writeAaclUsageCsvReport(filter, outputStream);
        verify(reportRepository);
    }

    @Test
    public void testWriteFasScenarioUsagesCsvReportStatusSentToLM() {
        Scenario scenario = buildScenario(ScenarioStatusEnum.SENT_TO_LM);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        reportRepository.writeArchivedFasScenarioUsagesCsvReport(scenario.getId(), outputStream);
        expectLastCall().once();
        replay(reportRepository);
        reportService.writeFasScenarioUsagesCsvReport(scenario, outputStream);
        verify(reportRepository);
    }

    @Test
    public void testWriteNtsScenarioUsagesCsvReportStatusSentToLM() {
        Scenario scenario = buildScenario(ScenarioStatusEnum.SENT_TO_LM);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        reportRepository.writeArchivedNtsScenarioUsagesCsvReport(scenario.getId(), outputStream);
        expectLastCall().once();
        replay(reportRepository);
        reportService.writeNtsScenarioUsagesCsvReport(scenario, outputStream);
        verify(reportRepository);
    }

    @Test
    public void testWriteFasScenarioUsagesCsvReportStatusArchived() {
        Scenario scenario = buildScenario(ScenarioStatusEnum.ARCHIVED);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        reportRepository.writeArchivedFasScenarioUsagesCsvReport(scenario.getId(), outputStream);
        expectLastCall().once();
        replay(reportRepository);
        reportService.writeFasScenarioUsagesCsvReport(scenario, outputStream);
        verify(reportRepository);
    }

    @Test
    public void testWriteNtsScenarioUsagesCsvReportStatusArchived() {
        Scenario scenario = buildScenario(ScenarioStatusEnum.ARCHIVED);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        reportRepository.writeArchivedNtsScenarioUsagesCsvReport(scenario.getId(), outputStream);
        expectLastCall().once();
        replay(reportRepository);
        reportService.writeNtsScenarioUsagesCsvReport(scenario, outputStream);
        verify(reportRepository);
    }

    @Test
    public void testWriteFasScenarioUsagesCsvReportStatusInProgress() {
        Scenario scenario = buildScenario(ScenarioStatusEnum.IN_PROGRESS);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        reportRepository.writeFasScenarioUsagesCsvReport(scenario.getId(), outputStream);
        expectLastCall().once();
        replay(reportRepository);
        reportService.writeFasScenarioUsagesCsvReport(scenario, outputStream);
        verify(reportRepository);
    }

    @Test
    public void testWriteNtsScenarioUsagesCsvReportStatusInProgress() {
        Scenario scenario = buildScenario(ScenarioStatusEnum.IN_PROGRESS);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        reportRepository.writeNtsScenarioUsagesCsvReport(scenario.getId(), outputStream);
        expectLastCall().once();
        replay(reportRepository);
        reportService.writeNtsScenarioUsagesCsvReport(scenario, outputStream);
        verify(reportRepository);
    }

    @Test
    public void testWriteScenarioRightsholderTotalsCsvReportStatusArchived() {
        Scenario scenario = buildScenario(ScenarioStatusEnum.ARCHIVED);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        reportRepository.writeArchivedScenarioRightsholderTotalsCsvReport(scenario.getId(), outputStream);
        expectLastCall().once();
        replay(reportRepository);
        reportService.writeScenarioRightsholderTotalsCsvReport(scenario, outputStream);
        verify(reportRepository);
    }

    @Test
    public void testWriteScenarioRightsholderTotalsCsvReportStatusInProgress() {
        Scenario scenario = buildScenario(ScenarioStatusEnum.IN_PROGRESS);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        reportRepository.writeScenarioRightsholderTotalsCsvReport(scenario.getId(), outputStream);
        expectLastCall().once();
        replay(reportRepository);
        reportService.writeScenarioRightsholderTotalsCsvReport(scenario, outputStream);
        verify(reportRepository);
    }

    @Test
    public void testWriteFasScenarioUsagesCsvReportStatusApproved() {
        Scenario scenario = buildScenario(ScenarioStatusEnum.APPROVED);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        reportRepository.writeFasScenarioUsagesCsvReport(scenario.getId(), outputStream);
        expectLastCall().once();
        replay(reportRepository);
        reportService.writeFasScenarioUsagesCsvReport(scenario, outputStream);
        verify(reportRepository);
    }

    @Test
    public void testWriteNtsScenarioUsagesCsvReportStatusApproved() {
        Scenario scenario = buildScenario(ScenarioStatusEnum.APPROVED);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        reportRepository.writeNtsScenarioUsagesCsvReport(scenario.getId(), outputStream);
        expectLastCall().once();
        replay(reportRepository);
        reportService.writeNtsScenarioUsagesCsvReport(scenario, outputStream);
        verify(reportRepository);
    }

    @Test
    public void testWriteFasScenarioUsagesCsvReportStatusSubmitted() {
        Scenario scenario = buildScenario(ScenarioStatusEnum.SUBMITTED);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        reportRepository.writeFasScenarioUsagesCsvReport(scenario.getId(), outputStream);
        expectLastCall().once();
        replay(reportRepository);
        reportService.writeFasScenarioUsagesCsvReport(scenario, outputStream);
        verify(reportRepository);
    }

    @Test
    public void testWriteNtsScenarioUsagesCsvReportStatusSubmitted() {
        Scenario scenario = buildScenario(ScenarioStatusEnum.SUBMITTED);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        reportRepository.writeNtsScenarioUsagesCsvReport(scenario.getId(), outputStream);
        expectLastCall().once();
        replay(reportRepository);
        reportService.writeNtsScenarioUsagesCsvReport(scenario, outputStream);
        verify(reportRepository);
    }

    @Test
    public void testWriteFasBatchSummaryCsvReport() {
        ByteArrayOutputStream outputStream = createMock(ByteArrayOutputStream.class);
        reportRepository.writeFasBatchSummaryCsvReport(outputStream);
        expectLastCall().once();
        replay(reportRepository);
        reportService.writeFasBatchSummaryCsvReport(outputStream);
        verify(reportRepository);
    }

    @Test
    public void testWriteNtsWithdrawnBatchSummaryReport() {
        ByteArrayOutputStream outputStream = createMock(ByteArrayOutputStream.class);
        reportRepository.writeNtsWithdrawnBatchSummaryCsvReport(outputStream);
        expectLastCall().once();
        replay(reportRepository);
        reportService.writeNtsWithdrawnBatchSummaryCsvReport(outputStream);
        verify(reportRepository);
    }

    @Test
    public void testWriteResearchStatusCsvReport() {
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        reportRepository.writeResearchStatusCsvReport(outputStream);
        expectLastCall().once();
        replay(reportRepository);
        reportService.writeResearchStatusCsvReport(outputStream);
        verify(reportRepository);
    }

    @Test
    public void testWriteSummaryMarketCsvReport() {
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        reportRepository.writeSummaryMarketCsvReport(Collections.emptyList(), outputStream);
        expectLastCall().once();
        replay(reportRepository);
        reportService.writeSummaryMarkerCsvReport(Collections.emptyList(), outputStream);
        verify(reportRepository);
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
        reportRepository.writeOwnershipAdjustmentCsvReport(scenarioId, statuses, outputStream);
        expectLastCall().once();
        replay(reportRepository);
        reportService.writeOwnershipAdjustmentCsvReport(scenarioId, statuses, outputStream);
        verify(reportRepository);
    }

    @Test
    public void testWriteWorkClassificationCsvReportWithBatchIds() {
        Set<String> batchIds = Collections.singleton("batch-uid-1293213");
        String search = "search";
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        reportRepository.writeWorkClassificationCsvReport(batchIds, search, outputStream);
        expectLastCall().once();
        replay(reportRepository);
        reportService.writeWorkClassificationCsvReport(batchIds, search, outputStream);
        verify(reportRepository);
    }


    @Test
    public void testWriteWorkClassificationCsvReportWithSearch() {
        String search = "search";
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        reportRepository.writeWorkClassificationCsvReport(search, outputStream);
        expectLastCall().once();
        replay(reportRepository);
        reportService.writeWorkClassificationCsvReport(Collections.EMPTY_SET, search, outputStream);
        verify(reportRepository);
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
