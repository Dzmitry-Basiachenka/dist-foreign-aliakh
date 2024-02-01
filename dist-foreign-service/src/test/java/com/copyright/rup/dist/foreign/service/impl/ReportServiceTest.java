package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.common.test.ReportTestUtils;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.RhTaxInformation;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IAaclReportRepository;
import com.copyright.rup.dist.foreign.repository.api.IAclciReportRepository;
import com.copyright.rup.dist.foreign.repository.api.IFasReportRepository;
import com.copyright.rup.dist.foreign.repository.api.INtsReportRepository;
import com.copyright.rup.dist.foreign.repository.api.ISalReportRepository;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IRhTaxService;
import com.copyright.rup.dist.foreign.service.api.fas.IFasUsageService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
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
    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private static final BigDecimal USAGE_BATCH_GROSS_AMOUNT = BigDecimal.ONE;
    private static final BigDecimal DEFAULT_ESTIMATED_SERVICE_FEE = new BigDecimal("0.18500");
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    }

    private final ReportTestUtils reportTestUtils = new ReportTestUtils(PATH_TO_EXPECTED_REPORTS);

    private IReportService reportService;
    private IAaclReportRepository aaclReportRepository;
    private IAclciReportRepository aclciReportRepository;
    private IFasReportRepository fasReportRepository;
    private INtsReportRepository ntsReportRepository;
    private ISalReportRepository salReportRepository;
    private IRhTaxService rhTaxService;

    @Before
    public void setUp() {
        reportService = new ReportService();
        aaclReportRepository = createMock(IAaclReportRepository.class);
        aclciReportRepository = createMock(IAclciReportRepository.class);
        fasReportRepository = createMock(IFasReportRepository.class);
        ntsReportRepository = createMock(INtsReportRepository.class);
        salReportRepository = createMock(ISalReportRepository.class);
        rhTaxService = createMock(IRhTaxService.class);
        Whitebox.setInternalState(reportService, aaclReportRepository);
        Whitebox.setInternalState(reportService, aclciReportRepository);
        Whitebox.setInternalState(reportService, fasReportRepository);
        Whitebox.setInternalState(reportService, ntsReportRepository);
        Whitebox.setInternalState(reportService, salReportRepository);
        Whitebox.setInternalState(reportService, rhTaxService);
        Whitebox.setInternalState(reportService, "defaultEstimatedServiceFee", DEFAULT_ESTIMATED_SERVICE_FEE);
    }

    @Test
    public void testWriteUndistributedLiabilitiesCsvReport() {
        LocalDate paymentDate = LocalDate.now();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        fasReportRepository.writeUndistributedLiabilitiesCsvReport(paymentDate, outputStream,
            DEFAULT_ESTIMATED_SERVICE_FEE, Set.of("FAS", "FAS2"));
        expectLastCall().once();
        replay(fasReportRepository);
        reportService.writeUndistributedLiabilitiesCsvReport(paymentDate, outputStream, Set.of("FAS", "FAS2"));
        verify(fasReportRepository);
    }

    @Test
    public void testWriteAaclBaselineUsagesCsvReport() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        aaclReportRepository.writeAaclBaselineUsagesCsvReport(2, outputStream);
        expectLastCall().once();
        replay(aaclReportRepository);
        reportService.writeAaclBaselineUsagesCsvReport(2, outputStream);
        verify(aaclReportRepository);
    }

    @Test
    public void testWriteFasServiceFeeTrueUpCsvReport() {
        LocalDate fromDate = LocalDate.now();
        LocalDate toDate = LocalDate.now();
        LocalDate paymentDateTo = LocalDate.now();
        IFasUsageService fasUsageService = createMock(IFasUsageService.class);
        Whitebox.setInternalState(reportService, fasUsageService);
        expect(fasUsageService.getClaAccountNumber()).andReturn(2000017000L).once();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        fasReportRepository.writeFasServiceFeeTrueUpCsvReport(fromDate, toDate, paymentDateTo, outputStream,
            2000017000L, DEFAULT_ESTIMATED_SERVICE_FEE);
        expectLastCall().once();
        replay(fasReportRepository, fasUsageService);
        reportService.writeFasServiceFeeTrueUpCsvReport(fromDate, toDate, paymentDateTo, outputStream);
        verify(fasReportRepository, fasUsageService);
    }

    @Test
    public void testWriteNtsServiceFeeTrueUpCsvReport() {
        Scenario scenario = new Scenario();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ntsReportRepository.writeNtsServiceFeeTrueUpCsvReport(scenario, outputStream, DEFAULT_ESTIMATED_SERVICE_FEE);
        expectLastCall().once();
        replay(ntsReportRepository);
        reportService.writeNtsServiceFeeTrueUpCsvReport(scenario, outputStream);
        verify(ntsReportRepository);
    }

    @Test
    public void testWriteFasUsageCsvReport() {
        UsageFilter filter = createMock(UsageFilter.class);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        fasReportRepository.writeFasUsageCsvReport(filter, outputStream);
        expectLastCall().once();
        replay(fasReportRepository);
        reportService.writeFasUsageCsvReport(filter, outputStream);
        verify(fasReportRepository);
    }

    @Test
    public void testWriteNtsUsageCsvReport() {
        UsageFilter filter = createMock(UsageFilter.class);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        ntsReportRepository.writeNtsUsageCsvReport(filter, outputStream);
        expectLastCall().once();
        replay(ntsReportRepository);
        reportService.writeNtsUsageCsvReport(filter, outputStream);
        verify(ntsReportRepository);
    }

    @Test
    public void testWriteWorkSharesByAggLcClassSummaryCsvReport() {
        Scenario scenario = buildScenario(ScenarioStatusEnum.IN_PROGRESS);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        aaclReportRepository
            .writeWorkSharesByAggLcClassSummaryCsvReport(scenario.getId(), scenario.getStatus(), outputStream);
        expectLastCall().once();
        replay(aaclReportRepository);
        reportService.writeWorkSharesByAggLcClassSummaryCsvReport(scenario, outputStream);
        verify(aaclReportRepository);
    }

    @Test
    public void testWriteWorkSharesByAggLcClassCsvReport() {
        Scenario scenario = buildScenario(ScenarioStatusEnum.IN_PROGRESS);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        aaclReportRepository.writeWorkSharesByAggLcClassCsvReport(scenario.getId(), scenario.getStatus(), outputStream);
        expectLastCall().once();
        replay(aaclReportRepository);
        reportService.writeWorkSharesByAggLcClassCsvReport(scenario, outputStream);
        verify(aaclReportRepository);
    }

    @Test
    public void testWriteFasAuditCsvReport() {
        AuditFilter filter = createMock(AuditFilter.class);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        fasReportRepository.writeAuditFasCsvReport(filter, outputStream);
        expectLastCall().once();
        replay(fasReportRepository);
        reportService.writeAuditFasCsvReport(filter, outputStream);
        verify(fasReportRepository);
    }

    @Test
    public void testWriteAuditNtsCsvReport() {
        AuditFilter filter = createMock(AuditFilter.class);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        ntsReportRepository.writeAuditNtsCsvReport(filter, outputStream);
        expectLastCall().once();
        replay(ntsReportRepository);
        reportService.writeAuditNtsCsvReport(filter, outputStream);
        verify(ntsReportRepository);
    }

    @Test
    public void testWriteAuditSalCsvReport() {
        AuditFilter filter = createMock(AuditFilter.class);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        salReportRepository.writeAuditSalCsvReport(filter, outputStream);
        expectLastCall().once();
        replay(salReportRepository);
        reportService.writeAuditSalCsvReport(filter, outputStream);
        verify(salReportRepository);
    }

    @Test
    public void testWriteAaclUsageCsvReport() {
        UsageFilter filter = createMock(UsageFilter.class);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        aaclReportRepository.writeAaclUsagesCsvReport(filter, outputStream);
        expectLastCall().once();
        replay(aaclReportRepository);
        reportService.writeAaclUsageCsvReport(filter, outputStream);
        verify(aaclReportRepository);
    }

    @Test
    public void testWriteAclciUsageCsvReport() {
        UsageFilter filter = createMock(UsageFilter.class);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        aclciReportRepository.writeAclciUsagesCsvReport(filter, outputStream);
        expectLastCall().once();
        replay(aclciReportRepository);
        reportService.writeAclciUsageCsvReport(filter, outputStream);
        verify(aclciReportRepository);
    }

    @Test
    public void testWriteSalUsageCsvReport() {
        UsageFilter filter = createMock(UsageFilter.class);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        salReportRepository.writeSalUsagesCsvReport(filter, outputStream);
        expectLastCall().once();
        replay(salReportRepository);
        reportService.writeSalUsageCsvReport(filter, outputStream);
        verify(salReportRepository);
    }

    @Test
    public void testWriteFasScenarioUsagesCsvReportStatusSentToLM() {
        Scenario scenario = buildScenario(ScenarioStatusEnum.SENT_TO_LM);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        fasReportRepository.writeArchivedFasScenarioUsagesCsvReport(scenario.getId(), outputStream);
        expectLastCall().once();
        replay(fasReportRepository);
        reportService.writeFasScenarioUsagesCsvReport(scenario, outputStream);
        verify(fasReportRepository);
    }

    @Test
    public void testWriteNtsScenarioUsagesCsvReportStatusSentToLM() {
        Scenario scenario = buildScenario(ScenarioStatusEnum.SENT_TO_LM);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        ntsReportRepository.writeArchivedNtsScenarioUsagesCsvReport(scenario.getId(), outputStream);
        expectLastCall().once();
        replay(ntsReportRepository);
        reportService.writeNtsScenarioUsagesCsvReport(scenario, outputStream);
        verify(ntsReportRepository);
    }

    @Test
    public void testWriteFasScenarioUsagesCsvReportStatusArchived() {
        Scenario scenario = buildScenario(ScenarioStatusEnum.ARCHIVED);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        fasReportRepository.writeArchivedFasScenarioUsagesCsvReport(scenario.getId(), outputStream);
        expectLastCall().once();
        replay(fasReportRepository);
        reportService.writeFasScenarioUsagesCsvReport(scenario, outputStream);
        verify(fasReportRepository);
    }

    @Test
    public void testWriteNtsScenarioUsagesCsvReportStatusArchived() {
        Scenario scenario = buildScenario(ScenarioStatusEnum.ARCHIVED);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        ntsReportRepository.writeArchivedNtsScenarioUsagesCsvReport(scenario.getId(), outputStream);
        expectLastCall().once();
        replay(ntsReportRepository);
        reportService.writeNtsScenarioUsagesCsvReport(scenario, outputStream);
        verify(ntsReportRepository);
    }

    @Test
    public void testWriteFasScenarioUsagesCsvReportStatusInProgress() {
        Scenario scenario = buildScenario(ScenarioStatusEnum.IN_PROGRESS);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        fasReportRepository.writeFasScenarioUsagesCsvReport(scenario.getId(), outputStream);
        expectLastCall().once();
        replay(fasReportRepository);
        reportService.writeFasScenarioUsagesCsvReport(scenario, outputStream);
        verify(fasReportRepository);
    }

    @Test
    public void testWriteNtsScenarioUsagesCsvReportStatusInProgress() {
        Scenario scenario = buildScenario(ScenarioStatusEnum.IN_PROGRESS);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        ntsReportRepository.writeNtsScenarioUsagesCsvReport(scenario.getId(), outputStream);
        expectLastCall().once();
        replay(ntsReportRepository);
        reportService.writeNtsScenarioUsagesCsvReport(scenario, outputStream);
        verify(ntsReportRepository);
    }

    @Test
    public void testWriteAaclScenarioUsagesCsvReportStatusArchived() {
        Scenario scenario = buildScenario(ScenarioStatusEnum.ARCHIVED);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        aaclReportRepository.writeArchivedAaclScenarioUsagesCsvReport(scenario.getId(), outputStream);
        expectLastCall().once();
        replay(aaclReportRepository);
        reportService.writeAaclScenarioUsagesCsvReport(scenario, outputStream);
        verify(aaclReportRepository);
    }

    @Test
    public void testWriteAaclScenarioUsagesCsvReportStatusInProgress() {
        Scenario scenario = buildScenario(ScenarioStatusEnum.IN_PROGRESS);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        aaclReportRepository.writeAaclScenarioUsagesCsvReport(scenario.getId(), outputStream);
        expectLastCall().once();
        replay(aaclReportRepository);
        reportService.writeAaclScenarioUsagesCsvReport(scenario, outputStream);
        verify(aaclReportRepository);
    }

    @Test
    public void testWriteSalScenarioUsagesCsvReportStatusArchived() {
        Scenario scenario = buildScenario(ScenarioStatusEnum.ARCHIVED);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        salReportRepository.writeArchivedSalScenarioUsagesCsvReport(scenario.getId(), outputStream);
        expectLastCall().once();
        replay(salReportRepository);
        reportService.writeSalScenarioUsagesCsvReport(scenario, outputStream);
        verify(salReportRepository);
    }

    @Test
    public void testWriteSalScenarioUsagesCsvReportStatusInProgress() {
        Scenario scenario = buildScenario(ScenarioStatusEnum.IN_PROGRESS);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        salReportRepository.writeSalScenarioUsagesCsvReport(scenario.getId(), outputStream);
        expectLastCall().once();
        replay(salReportRepository);
        reportService.writeSalScenarioUsagesCsvReport(scenario, outputStream);
        verify(salReportRepository);
    }

    @Test
    public void testWriteSalScenarioUsagesCsvReportStatusSentToLM() {
        Scenario scenario = buildScenario(ScenarioStatusEnum.SENT_TO_LM);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        salReportRepository.writeArchivedSalScenarioUsagesCsvReport(scenario.getId(), outputStream);
        expectLastCall().once();
        replay(salReportRepository);
        reportService.writeSalScenarioUsagesCsvReport(scenario, outputStream);
        verify(salReportRepository);
    }

    @Test
    public void testWriteScenarioRightsholderTotalsCsvReportStatusArchived() {
        Scenario scenario = buildScenario(ScenarioStatusEnum.ARCHIVED);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        fasReportRepository.writeArchivedScenarioRightsholderTotalsCsvReport(scenario.getId(), outputStream);
        expectLastCall().once();
        replay(fasReportRepository);
        reportService.writeScenarioRightsholderTotalsCsvReport(scenario, outputStream);
        verify(fasReportRepository);
    }

    @Test
    public void testWriteScenarioRightsholderTotalsCsvReportStatusInProgress() {
        Scenario scenario = buildScenario(ScenarioStatusEnum.IN_PROGRESS);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        fasReportRepository.writeScenarioRightsholderTotalsCsvReport(scenario.getId(), outputStream);
        expectLastCall().once();
        replay(fasReportRepository);
        reportService.writeScenarioRightsholderTotalsCsvReport(scenario, outputStream);
        verify(fasReportRepository);
    }

    @Test
    public void testWriteFasScenarioUsagesCsvReportStatusApproved() {
        Scenario scenario = buildScenario(ScenarioStatusEnum.APPROVED);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        fasReportRepository.writeFasScenarioUsagesCsvReport(scenario.getId(), outputStream);
        expectLastCall().once();
        replay(fasReportRepository);
        reportService.writeFasScenarioUsagesCsvReport(scenario, outputStream);
        verify(fasReportRepository);
    }

    @Test
    public void testWriteNtsScenarioUsagesCsvReportStatusApproved() {
        Scenario scenario = buildScenario(ScenarioStatusEnum.APPROVED);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        ntsReportRepository.writeNtsScenarioUsagesCsvReport(scenario.getId(), outputStream);
        expectLastCall().once();
        replay(ntsReportRepository);
        reportService.writeNtsScenarioUsagesCsvReport(scenario, outputStream);
        verify(ntsReportRepository);
    }

    @Test
    public void testWriteFasScenarioUsagesCsvReportStatusSubmitted() {
        Scenario scenario = buildScenario(ScenarioStatusEnum.SUBMITTED);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        fasReportRepository.writeFasScenarioUsagesCsvReport(scenario.getId(), outputStream);
        expectLastCall().once();
        replay(fasReportRepository);
        reportService.writeFasScenarioUsagesCsvReport(scenario, outputStream);
        verify(fasReportRepository);
    }

    @Test
    public void testWriteNtsScenarioUsagesCsvReportStatusSubmitted() {
        Scenario scenario = buildScenario(ScenarioStatusEnum.SUBMITTED);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        ntsReportRepository.writeNtsScenarioUsagesCsvReport(scenario.getId(), outputStream);
        expectLastCall().once();
        replay(ntsReportRepository);
        reportService.writeNtsScenarioUsagesCsvReport(scenario, outputStream);
        verify(ntsReportRepository);
    }

    @Test
    public void testWriteFasBatchSummaryCsvReport() {
        ByteArrayOutputStream outputStream = createMock(ByteArrayOutputStream.class);
        fasReportRepository.writeFasBatchSummaryCsvReport(outputStream);
        expectLastCall().once();
        replay(fasReportRepository);
        reportService.writeFasBatchSummaryCsvReport(outputStream);
        verify(fasReportRepository);
    }

    @Test
    public void testWriteNtsWithdrawnBatchSummaryReport() {
        ByteArrayOutputStream outputStream = createMock(ByteArrayOutputStream.class);
        ntsReportRepository.writeNtsWithdrawnBatchSummaryCsvReport(outputStream);
        expectLastCall().once();
        replay(ntsReportRepository);
        reportService.writeNtsWithdrawnBatchSummaryCsvReport(outputStream);
        verify(ntsReportRepository);
    }

    @Test
    public void testWriteResearchStatusCsvReport() {
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        fasReportRepository.writeResearchStatusCsvReport(outputStream);
        expectLastCall().once();
        replay(fasReportRepository);
        reportService.writeResearchStatusCsvReport(outputStream);
        verify(fasReportRepository);
    }

    @Test
    public void testWriteSummaryMarketCsvReport() {
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        fasReportRepository.writeSummaryMarketCsvReport(List.of(), outputStream);
        expectLastCall().once();
        replay(fasReportRepository);
        reportService.writeSummaryMarkerCsvReport(List.of(), outputStream);
        verify(fasReportRepository);
    }

    @Test
    public void testWriteNtsWithdrawnBatchesCsvReport() throws IOException {
        PipedOutputStream pos = new PipedOutputStream();
        PipedInputStream pis = new PipedInputStream(pos);
        new ReportService().writeNtsWithdrawnBatchesCsvReport(
            List.of(buildUsageBatch()), USAGE_BATCH_GROSS_AMOUNT, pos);
        reportTestUtils.assertCsvReport("batches_nts_withdrawn.csv", pis);
    }

    @Test
    public void testWriteOwnershipAdjustmentCsvReport() {
        String scenarioId = "12b3c369-3084-41ad-92b5-62197660d642";
        Set<RightsholderDiscrepancyStatusEnum> statuses = Set.of(RightsholderDiscrepancyStatusEnum.APPROVED);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        fasReportRepository.writeOwnershipAdjustmentCsvReport(scenarioId, statuses, outputStream);
        expectLastCall().once();
        replay(fasReportRepository);
        reportService.writeOwnershipAdjustmentCsvReport(scenarioId, statuses, outputStream);
        verify(fasReportRepository);
    }

    @Test
    public void testWriteWorkClassificationCsvReportWithBatchIds() {
        Set<String> batchIds = Set.of("batch-uid-1293213");
        String search = "search";
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        ntsReportRepository.writeWorkClassificationCsvReport(batchIds, search, outputStream);
        expectLastCall().once();
        replay(ntsReportRepository);
        reportService.writeWorkClassificationCsvReport(batchIds, search, outputStream);
        verify(ntsReportRepository);
    }

    @Test
    public void testWriteWorkClassificationCsvReportWithSearch() {
        String search = "search";
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        ntsReportRepository.writeWorkClassificationCsvReport(search, outputStream);
        expectLastCall().once();
        replay(ntsReportRepository);
        reportService.writeWorkClassificationCsvReport(Set.of(), search, outputStream);
        verify(ntsReportRepository);
    }

    @Test
    public void testFasWriteExcludeDetailsByPayeeCsvReport() {
        ExcludePayeeFilter filter = new ExcludePayeeFilter();
        Set<Long> selectedAccountNumbers = Set.of();
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        fasReportRepository.writeFasExcludeDetailsByPayeeCsvReport(filter, selectedAccountNumbers, outputStream);
        expectLastCall().once();
        replay(fasReportRepository);
        reportService.writeFasExcludeDetailsByPayeeCsvReport(filter, selectedAccountNumbers, outputStream);
        verify(fasReportRepository);
    }

    @Test
    public void testAaclWriteExcludeDetailsByPayeeCsvReport() {
        ExcludePayeeFilter filter = new ExcludePayeeFilter();
        Set<Long> selectedAccountNumbers = Set.of();
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        aaclReportRepository.writeAaclExcludeDetailsByPayeeCsvReport(filter, selectedAccountNumbers, outputStream);
        expectLastCall().once();
        replay(aaclReportRepository);
        reportService.writeAaclExcludeDetailsByPayeeCsvReport(filter, selectedAccountNumbers, outputStream);
        verify(aaclReportRepository);
    }

    @Test
    public void testWriteAaclUndistributedLiabilitiesCsvReport() {
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        aaclReportRepository.writeAaclUndistributedLiabilitiesCsvReport(outputStream);
        expectLastCall().once();
        replay(aaclReportRepository);
        reportService.writeAaclUndistributedLiabilitiesCsvReport(outputStream);
        verify(aaclReportRepository);
    }

    @Test
    public void testWriteNtsUndistributedLiabilitiesCsvReport() {
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        ntsReportRepository.writeNtsUndistributedLiabilitiesCsvReport(DEFAULT_ESTIMATED_SERVICE_FEE, outputStream);
        expectLastCall().once();
        replay(ntsReportRepository);
        reportService.writeNtsUndistributedLiabilitiesReport(outputStream);
        verify(ntsReportRepository);
    }

    @Test
    public void testWriteSalLiabilitiesByRhReport() {
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        List<Scenario> scenarios =
            List.of(buildScenario(ScenarioStatusEnum.IN_PROGRESS), buildScenario(ScenarioStatusEnum.SUBMITTED));
        salReportRepository.writeSalLiabilitiesByRhCsvReport(scenarios, outputStream);
        expectLastCall().once();
        replay(salReportRepository);
        reportService.writeSalLiabilitiesByRhReport(scenarios, outputStream);
        verify(salReportRepository);
    }

    @Test
    public void writeTaxNotificationCsvReport() throws IOException {
        Set<String> scenarioIds = Set.of("629c078f-462f-4ba6-bebd-8d558ccc12aa");
        expect(rhTaxService.getRhTaxInformation(FAS_PRODUCT_FAMILY, scenarioIds, 15))
            .andReturn(loadExpectedRhTaxInformation("json/rh_tax_information_for_tax_notification_report.json")).once();
        replay(rhTaxService);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        reportService.writeTaxNotificationCsvReport(FAS_PRODUCT_FAMILY, scenarioIds, 15, outputStream);
        reportTestUtils.assertCsvReport("tax_notification.csv", new ByteArrayInputStream(outputStream.toByteArray()));
        verify(rhTaxService);
    }

    @Test
    public void testWriteSalLiabilitiesSummaryByRhAndWorkCsvReport() {
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        List<Scenario> scenarios = List.of(new Scenario());
        salReportRepository.writeSalLiabilitiesSummaryByRhAndWorkCsvReport(scenarios, outputStream);
        expectLastCall().once();
        replay(salReportRepository);
        reportService.writeSalLiabilitiesSummaryByRhAndWorkCsvReport(scenarios, outputStream);
        verify(salReportRepository);
    }

    @Test
    public void testWriteSalUndistributedLiabilitiesCsvReport() {
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        salReportRepository.writeSalUndistributedLiabilitiesCsvReport(outputStream);
        expectLastCall().once();
        replay(salReportRepository);
        reportService.writeSalUndistributedLiabilitiesCsvReport(outputStream);
    }

    @Test
    public void testWriteSalFundPoolsCsvReport() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        salReportRepository.writeSalFundPoolsCsvReport(2020, outputStream);
        expectLastCall().once();
        replay(salReportRepository);
        reportService.writeSalFundPoolsCsvReport(2020, outputStream);
        verify(salReportRepository);
    }

    @Test
    public void testWriteSalFundPoolsListCsvReport() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        salReportRepository.writeSalFundPoolsCsvReport(outputStream);
        expectLastCall().once();
        replay(salReportRepository);
        reportService.writeSalFundPoolsCsvReport(outputStream);
        verify(salReportRepository);
    }

    @Test
    public void testWriteSalHistoricalItemBankDetailsReport() {
        Long licenseeAccountNumber = 5588L;
        int periodEndYearFrom = 2019;
        int periodEndYearTo = 2020;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        salReportRepository.writeSalHistoricalItemBankDetailsReport(licenseeAccountNumber, periodEndYearFrom,
            periodEndYearTo, outputStream);
        expectLastCall().once();
        replay(salReportRepository);
        reportService.writeSalHistoricalItemBankDetailsReport(licenseeAccountNumber, periodEndYearFrom,
            periodEndYearTo, outputStream);
        verify(salReportRepository);
    }

    @Test
    public void writeNtsFundPoolsCsvReport() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ntsReportRepository.writeNtsFundPoolsCsvReport(outputStream);
        replay(ntsReportRepository);
        reportService.writeNtsFundPoolsCsvReport(outputStream);
        verify(ntsReportRepository);
    }

    @Test
    public void testWriteNtsPreServiceFeeFundCsvReport() {
        //TODO: {dbasiachenka} implement
    }

    private Scenario buildScenario(ScenarioStatusEnum status) {
        var scenario = new Scenario();
        scenario.setId("3049d34e-85ea-45d8-affc-d69dc9e33085");
        scenario.setStatus(status);
        return scenario;
    }

    private UsageBatch buildUsageBatch() {
        var usageBatch = new UsageBatch();
        usageBatch.setId("2358deb3-caa3-4c4e-85cd-c353fcc8e6b9");
        usageBatch.setName("Copibec 25May18");
        usageBatch.setGrossAmount(USAGE_BATCH_GROSS_AMOUNT);
        return usageBatch;
    }

    private List<RhTaxInformation> loadExpectedRhTaxInformation(String fileName) throws IOException {
        String content = TestUtils.fileToString(this.getClass(), fileName);
        return OBJECT_MAPPER.readValue(content, new TypeReference<List<RhTaxInformation>>() {});
    }
}
