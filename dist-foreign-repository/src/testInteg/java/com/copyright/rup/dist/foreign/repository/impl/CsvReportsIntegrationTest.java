package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.test.ReportTestUtils;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IReportRepository;

import com.google.common.collect.Sets;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * Integration tests for csv reports.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 05/21/18
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=csv-reports-test-data-init.groovy"})
@TransactionConfiguration
@Transactional
public class CsvReportsIntegrationTest {

    private static final String SEARCH_WITH_SQL_1 = "%";
    private static final String SEARCH_WITH_SQL_2 = "_";
    private static final BigDecimal DEFAULT_ESTIMATED_SERVICE_FEE = new BigDecimal("0.18500");

    @Autowired
    private IReportRepository reportRepository;

    private final ReportTestUtils reportTestUtils =
        new ReportTestUtils("src/testInteg/resources/com/copyright/rup/dist/foreign/repository/impl/csv");

    @BeforeClass
    public static void setUpTestDirectory() throws IOException {
        ReportTestUtils.setUpTestDirectory(CsvReportsIntegrationTest.class);
    }

    @Test
    public void testWriteAuditFasCsvReport() throws Exception {
        AuditFilter auditFilter = new AuditFilter();
        auditFilter.setBatchesIds(Sets.newHashSet("e855bf85-236c-42e7-9b12-8d68dd747bbe",
            "02a09322-5f0f-4cae-888c-73127050dc98", "d016d9c2-5460-41bf-837c-8598cf00b654"));
        auditFilter.setProductFamily("FAS");
        assertFilesWithExecutor(outputStream -> reportRepository.writeAuditFasCsvReport(auditFilter, outputStream),
            "audit_usages_fas_report.csv");
    }

    @Test
    public void testWriteAuditFas2CsvReport() throws Exception {
        AuditFilter auditFilter = new AuditFilter();
        auditFilter.setBatchesIds(
            Sets.newHashSet("034873b3-97fa-475a-9a2a-191e8ec988b3", "acae006c-a4fe-45f0-a0cc-098e12db00c5"));
        auditFilter.setProductFamily("FAS2");
        assertFilesWithExecutor(outputStream -> reportRepository.writeAuditFasCsvReport(auditFilter, outputStream),
            "audit_usages_fas2_report.csv");
    }

    @Test
    public void testWriteAuditNtsCsvReport() throws Exception {
        AuditFilter auditFilter = new AuditFilter();
        auditFilter.setBatchesIds(
            Sets.newHashSet("0c0a379a-461c-4e84-8062-326ece3c1f65", "f20ac1a3-eee4-4027-b5fb-def9adf0f871"));
        auditFilter.setProductFamily("NTS");
        assertFilesWithExecutor(outputStream -> reportRepository.writeAuditNtsCsvReport(auditFilter, outputStream),
            "audit_usages_nts_report.csv");
    }

    @Test
    public void testWriteAuditFasCsvReportForPostDistribution() throws Exception {
        AuditFilter auditFilter = new AuditFilter();
        auditFilter.setSearchValue("75693c90-d6f5-401a-8c26-134adc9745c5");
        assertFilesWithExecutor(outputStream -> reportRepository.writeAuditFasCsvReport(auditFilter, outputStream),
            "audit_usages_report_post_distribution.csv");
    }

    @Test
    public void testWriteAuditFasCsvReportEmptyCsvReport() throws Exception {
        assertEmptyAuditFasReport(new AuditFilter());
    }

    @Test
    public void testWriteAuditNtsCsvReportEmptyCsvReport() throws Exception {
        assertEmptyAuditNtsReport(new AuditFilter());
    }

    @Test
    public void testWriteAuditFasCsvReportSearchBySqlLikePattern() throws Exception {
        AuditFilter filter = new AuditFilter();
        filter.setSearchValue(SEARCH_WITH_SQL_1);
        assertEmptyAuditFasReport(filter);
        filter.setSearchValue(SEARCH_WITH_SQL_2);
        assertEmptyAuditFasReport(filter);
        filter = new AuditFilter();
        filter.setCccEventId(SEARCH_WITH_SQL_1);
        assertEmptyAuditFasReport(filter);
        filter.setCccEventId(SEARCH_WITH_SQL_2);
        assertEmptyAuditFasReport(filter);
        filter = new AuditFilter();
        filter.setDistributionName(SEARCH_WITH_SQL_1);
        assertEmptyAuditFasReport(filter);
        filter.setDistributionName(SEARCH_WITH_SQL_2);
        assertEmptyAuditFasReport(filter);
    }

    @Test
    public void testWriteAuditNtsCsvReportSearchBySqlLikePattern() throws Exception {
        AuditFilter filter = new AuditFilter();
        filter.setSearchValue(SEARCH_WITH_SQL_1);
        assertEmptyAuditNtsReport(filter);
        filter.setSearchValue(SEARCH_WITH_SQL_2);
        assertEmptyAuditNtsReport(filter);
        filter = new AuditFilter();
        filter.setCccEventId(SEARCH_WITH_SQL_1);
        assertEmptyAuditNtsReport(filter);
        filter.setCccEventId(SEARCH_WITH_SQL_2);
        assertEmptyAuditNtsReport(filter);
        filter = new AuditFilter();
        filter.setDistributionName(SEARCH_WITH_SQL_1);
        assertEmptyAuditNtsReport(filter);
        filter.setDistributionName(SEARCH_WITH_SQL_2);
        assertEmptyAuditNtsReport(filter);
    }

    @Test
    public void testWriteFasUsagesCsvReport() throws IOException {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setUsageBatchesIds(Sets.newHashSet("4c9cc089-b812-42cf-a5d2-1f5eda51fa76",
            "02a09322-5f0f-4cae-888c-73127050dc98"));
        assertFilesWithExecutor(outputStream -> reportRepository.writeFasUsageCsvReport(usageFilter, outputStream),
            "fas_usages_report.csv");
    }

    @Test
    public void testWriteFasUsagesEmptyCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeFasUsageCsvReport(new UsageFilter(), outputStream), "fas_usages_report_empty.csv");
    }

    @Test
    public void testWriteNtsUsagesCsvReport() throws IOException {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setUsageBatchesIds(Sets.newHashSet("f20ac1a3-eee4-4027-b5fb-def9adf0f871",
            "c0c07d51-2216-43c3-b61b-b904d86ec36a"));
        assertFilesWithExecutor(outputStream -> reportRepository.writeNtsUsageCsvReport(usageFilter, outputStream),
            "nts_usages_report.csv");
    }

    @Test
    public void testWriteNtsUsagesEmptyCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeNtsUsageCsvReport(new UsageFilter(), outputStream), "nts_usages_report_empty.csv");
    }

    @Test
    public void testWriteFasScenarioUsagesCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeFasScenarioUsagesCsvReport("12ec845f-0e76-4d1c-85cd-bb3fb7ca260e",
                outputStream), "scenario_fas_usages_report.csv");
    }

    @Test
    public void testWriteNtsScenarioUsagesCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeNtsScenarioUsagesCsvReport("85ba864e-1939-4a60-9fab-888b84199321",
                outputStream), "scenario_nts_usages_report.csv");
    }

    @Test
    public void testExportScenarioRightsholderTotalsCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeScenarioRightsholderTotalsCsvReport("12ec845f-0e76-4d1c-85cd-bb3fb7ca260e",
                outputStream), "scenario_rightsholder_totals_report.csv");
    }

    @Test
    public void testWriteUsagesForResearchAndFindIds() throws IOException {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setUsageStatus(UsageStatusEnum.WORK_NOT_FOUND);
        assertFiles(outputStream -> reportRepository.writeUsagesForResearchAndFindIds(usageFilter, outputStream),
            "usages_for_research.csv");
    }

    @Test
    public void testWriteUsagesForResearchAndFindIdsEmptyReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeUsagesForResearchAndFindIds(new UsageFilter(), outputStream),
            "usages_for_research_empty.csv");
    }

    @Test
    public void testWriteUndistributedLiabilitiesCsvReport() throws Exception {
        assertFiles(outputStream -> reportRepository.writeUndistributedLiabilitiesCsvReport(LocalDate.of(2011, 5, 5),
            outputStream, DEFAULT_ESTIMATED_SERVICE_FEE), "undistributed_liabilities_report.csv");
    }

    @Test
    public void testWriteUndistributedLiabilitiesCsvEmptyReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeUndistributedLiabilitiesCsvReport(LocalDate.of(2001, 5, 5),
            outputStream, DEFAULT_ESTIMATED_SERVICE_FEE), "undistributed_liabilities_report_empty.csv");
    }

    @Test
    public void testWriteResearchStatusCsvReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeResearchStatusCsvReport(outputStream),
            "research_status_report.csv");
    }

    @Test
    public void testWriteSummaryMarketCsvReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeSummaryMarketCsvReport(
            Arrays.asList("d016d9c2-5460-41bf-837c-8598cf00b651", "d016d9c2-5460-41bf-837c-8598cf00b652",
                "f1a40b56-54f1-4a46-90fa-77946c2f7805", "d016d9c2-5460-41bf-837c-8598cf00b658"), outputStream),
            "summary_of_market_report.csv");
    }

    @Test
    public void testWriteFasBatchSummaryCsvReport() throws IOException {
        assertFiles(outputStream ->
            reportRepository.writeFasBatchSummaryCsvReport(outputStream), "fas_batch_summary_report.csv");
    }

    @Test
    public void testWriteNtsWithdrawnBatchSummaryCsvReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeNtsWithdrawnBatchSummaryCsvReport(outputStream),
            "nts_withdrawn_batch_summary_report.csv");
    }

    @Test
    public void testWriteServiceFeeTrueUpCsvReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeServiceFeeTrueUpCsvReport(LocalDate.of(2012, 1, 1),
            LocalDate.of(2012, 3, 15), LocalDate.of(2014, 5, 5), outputStream, 2000017000L,
            DEFAULT_ESTIMATED_SERVICE_FEE), "service_fee_true_up_report.csv");
    }

    @Test
    public void testWriteServiceFeeTrueUpCsvEmptyReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeServiceFeeTrueUpCsvReport(LocalDate.of(2013, 1, 1),
            LocalDate.of(2012, 1, 1), LocalDate.of(2014, 5, 5), outputStream, 2000017000L,
            DEFAULT_ESTIMATED_SERVICE_FEE), "service_fee_true_up_empty_report.csv");
    }

    @Test
    public void testWriteOwnershipAdjustmentCsvReport() throws Exception {
        assertFiles(outputStream -> reportRepository.writeOwnershipAdjustmentCsvReport(
            "3210b236-1239-4a60-9fab-888b84199321",
            Collections.singleton(RightsholderDiscrepancyStatusEnum.APPROVED), outputStream),
            "ownership_adjustment_report.csv");
    }

    @Test
    public void testWriteOwnershipAdjustmentCsvEmptyReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeOwnershipAdjustmentCsvReport(
            "3210b236-1239-4a60-9fab-888b84199321",
            Collections.singleton(RightsholderDiscrepancyStatusEnum.DRAFT), outputStream),
            "ownership_adjustment_report_empty.csv");
    }

    @Test
    public void testWriteWorkClassificationCsvReportWithBatchIds() throws Exception {
        assertFilesWithExecutor(outputStream ->
                reportRepository.writeWorkClassificationCsvReport(
                    Collections.singleton("e17ebc80-e74e-436d-ba6e-acf3d355b7ff"), "987654321", outputStream),
            "work_classification_report_by_batch_ids.csv");
    }

    @Test
    public void testWriteWorkClassificationCsvReportWithEmptyBatchIds() throws Exception {
        assertFilesWithExecutor(outputStream ->
                reportRepository.writeWorkClassificationCsvReport(Collections.singleton("not-existing-uid"),
                    null, outputStream),
            "work_classification_report_empty.csv");
    }

    @Test
    public void testWriteWorkClassificationCsvReportWithSearch() throws Exception {
        assertFilesWithExecutor(
            outputStream -> reportRepository.writeWorkClassificationCsvReport("987654321", outputStream),
            "work_classification_report_by_search.csv");
    }

    @Test
    public void testWriteWorkClassificationCsvReportWithEmptySearch() throws Exception {
        assertFilesWithExecutor(
            outputStream -> reportRepository.writeWorkClassificationCsvReport("99999999", outputStream),
            "work_classification_report_empty.csv");
    }

    private void assertFiles(Consumer<ByteArrayOutputStream> reportWriter, String fileName) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        reportWriter.accept(outputStream);
        reportTestUtils.assertCsvReport(fileName, new ByteArrayInputStream(outputStream.toByteArray()));
    }

    private void assertFilesWithExecutor(Consumer<PipedOutputStream> reportWriter, String fileName)
        throws IOException {
        PipedOutputStream outputStream = new PipedOutputStream();
        Executors.newSingleThreadExecutor().execute(() -> reportWriter.accept(outputStream));
        reportTestUtils.assertCsvReport(fileName, new PipedInputStream(outputStream));
    }

    private void assertEmptyAuditFasReport(AuditFilter filter) throws IOException {
        assertFilesWithExecutor(outputStream -> reportRepository.writeAuditFasCsvReport(filter, outputStream),
            "audit_usages_report_empty.csv");
    }

    private void assertEmptyAuditNtsReport(AuditFilter filter) throws IOException {
        assertFilesWithExecutor(outputStream -> reportRepository.writeAuditFasCsvReport(filter, outputStream),
            "audit_usages_report_empty.csv");
    }
}
