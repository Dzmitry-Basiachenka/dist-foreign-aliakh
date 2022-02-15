package com.copyright.rup.dist.foreign.repository.impl.csv;

import com.copyright.rup.dist.common.test.ReportTestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IReportRepository;

import com.google.common.collect.Sets;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Collections;

/**
 * Integration tests for csv reports for NTS product family.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 06/02/21
 *
 * @author Anton Azarenka
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class NtsCsvReportsIntegrationTest extends CsvReportsTestHelper {

    private static final String FOLDER_NAME = "nts-csv-reports-integration-test/";
    private static final String WRITE_AUDIT_CSV_REPORT = FOLDER_NAME + "write-audit-csv-report.groovy";
    private static final String WRITE_USAGES_CSV_REPORT = FOLDER_NAME + "write-usages-csv-report.groovy";
    private static final String WRITE_SERVICE_FEE_TRUE_UP_CSV_REPORT =
        FOLDER_NAME + "write-service-fee-true-up-csv-report.groovy";
    private static final String WRITE_WORK_CLASSIFICATION_CSV_REPORT =
        FOLDER_NAME + "write-work-classification-csv-report.groovy";
    private static final String EMPTY_REPORT = "nts/audit_usages_report_empty.csv";

    @Autowired
    private IReportRepository reportRepository;

    @BeforeClass
    public static void setUpTestDirectory() throws IOException {
        ReportTestUtils.setUpTestDirectory(NtsCsvReportsIntegrationTest.class);
    }

    @Test
    @TestData(fileName = WRITE_AUDIT_CSV_REPORT)
    public void testWriteAuditCsvReport() throws Exception {
        AuditFilter auditFilter = new AuditFilter();
        auditFilter.setBatchesIds(Collections.singleton("0c0a379a-461c-4e84-8062-326ece3c1f65"));
        auditFilter.setProductFamily("NTS");
        assertFilesWithExecutor(outputStream -> reportRepository.writeAuditNtsCsvReport(auditFilter, outputStream),
            "nts/audit_usages_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_AUDIT_CSV_REPORT)
    public void testWriteAuditEmptyCsvReport() throws Exception {
        assertFilesWithExecutor(outputStream -> reportRepository.writeAuditNtsCsvReport(
            new AuditFilter(), outputStream), EMPTY_REPORT);
    }

    @Test
    @TestData(fileName = WRITE_AUDIT_CSV_REPORT)
    public void testWriteAuditCsvReportSearchBySqlLikePattern() throws Exception {
        AuditFilter filter1 = new AuditFilter();
        filter1.setSearchValue(SEARCH_WITH_PERCENT);
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeAuditNtsCsvReport(filter1, outputStream), EMPTY_REPORT);
        filter1.setSearchValue(SEARCH_WITH_UNDERSCORE);
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeAuditNtsCsvReport(filter1, outputStream), EMPTY_REPORT);
        AuditFilter filter2 = new AuditFilter();
        filter2.setCccEventId(SEARCH_WITH_PERCENT);
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeAuditNtsCsvReport(filter2, outputStream), EMPTY_REPORT);
        filter2.setCccEventId(SEARCH_WITH_UNDERSCORE);
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeAuditNtsCsvReport(filter2, outputStream), EMPTY_REPORT);
        AuditFilter filter3 = new AuditFilter();
        filter3.setDistributionName(SEARCH_WITH_PERCENT);
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeAuditNtsCsvReport(filter3, outputStream), EMPTY_REPORT);
        filter3.setDistributionName(SEARCH_WITH_UNDERSCORE);
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeAuditNtsCsvReport(filter3, outputStream), EMPTY_REPORT);
    }

    @Test
    @TestData(fileName = WRITE_USAGES_CSV_REPORT)
    public void testWriteUsagesCsvReport() throws IOException {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setUsageBatchesIds(Sets.newHashSet("f20ac1a3-eee4-4027-b5fb-def9adf0f871",
            "c0c07d51-2216-43c3-b61b-b904d86ec36a"));
        assertFilesWithExecutor(outputStream -> reportRepository.writeNtsUsageCsvReport(usageFilter, outputStream),
            "nts/usages_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_USAGES_CSV_REPORT)
    public void testWriteUsagesEmptyCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeNtsUsageCsvReport(new UsageFilter(), outputStream), "nts/usages_report_empty.csv");
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "write-scenario-usages-csv-report.groovy")
    public void testWriteScenarioUsagesCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeNtsScenarioUsagesCsvReport("85ba864e-1939-4a60-9fab-888b84199321",
                outputStream), "nts/scenario_usages_report.csv");
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "write-archived-scenario-usages-csv-report.groovy")
    public void testWriteArchivedScenarioUsagesCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeArchivedNtsScenarioUsagesCsvReport("1ead8a3e-1231-43a5-a3c5-ed766abe5a2f",
                outputStream), "nts/archive_scenario_usages_report.csv");
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "write-withdrawn-summary-csv-report.groovy")
    public void testWriteWithdrawnBatchSummaryCsvReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeNtsWithdrawnBatchSummaryCsvReport(outputStream),
            "nts/withdrawn_batch_summary_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_SERVICE_FEE_TRUE_UP_CSV_REPORT)
    public void testWriteServiceFeeTrueUpCsvReportWithFundPool() throws IOException {
        Scenario scenario = new Scenario();
        scenario.setId("a537da01-b211-4b81-b2b9-7dc0c791811a");
        scenario.setStatus(ScenarioStatusEnum.SENT_TO_LM);
        assertFiles(outputStream -> reportRepository.writeNtsServiceFeeTrueUpCsvReport(scenario, outputStream,
            DEFAULT_ESTIMATED_SERVICE_FEE), "nts/service_fee_true_up_report_1.csv");
    }

    @Test
    @TestData(fileName = WRITE_SERVICE_FEE_TRUE_UP_CSV_REPORT)
    public void testWriteServiceFeeTrueUpCsvReportWithoutFundPool() throws IOException {
        Scenario scenario = new Scenario();
        scenario.setId("dc6df4bd-7059-4975-8898-78b4a50d30b0");
        scenario.setStatus(ScenarioStatusEnum.SENT_TO_LM);
        assertFiles(outputStream -> reportRepository.writeNtsServiceFeeTrueUpCsvReport(scenario, outputStream,
            DEFAULT_ESTIMATED_SERVICE_FEE), "nts/service_fee_true_up_report_2.csv");
    }

    @Test
    @TestData(fileName = WRITE_SERVICE_FEE_TRUE_UP_CSV_REPORT)
    public void testWriteServiceFeeTrueUpEmptyCsvReport() throws IOException {
        Scenario scenario = new Scenario();
        scenario.setId("1871799a-157a-4fb2-82ab-9092bb3b6395");
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        assertFiles(outputStream -> reportRepository.writeNtsServiceFeeTrueUpCsvReport(scenario, outputStream,
            DEFAULT_ESTIMATED_SERVICE_FEE), "nts/service_fee_true_up_report_empty.csv");
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "write-undistributed-liabilities-csv-report.groovy")
    public void testWriteUndistributedLiabilitiesCsvReport() throws IOException {
        assertFiles(
            outputStream -> reportRepository.writeNtsUndistributedLiabilitiesCsvReport(DEFAULT_ESTIMATED_SERVICE_FEE,
                outputStream), "nts/undistributed_liabilities_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_WORK_CLASSIFICATION_CSV_REPORT)
    public void testWriteWorkClassificationCsvReportWithBatchIds() throws Exception {
        assertFilesWithExecutor(outputStream ->
                reportRepository.writeWorkClassificationCsvReport(
                    Collections.singleton("e17ebc80-e74e-436d-ba6e-acf3d355b7ff"), "987654321", outputStream),
            "nts/work_classification_report_by_batch_ids.csv");
    }

    @Test
    @TestData(fileName = WRITE_WORK_CLASSIFICATION_CSV_REPORT)
    public void testWriteWorkClassificationCsvReportWithEmptyBatchIds() throws Exception {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeWorkClassificationCsvReport(Collections.singleton("not-existing-uid"),
                null, outputStream), "nts/work_classification_report_empty.csv");
    }

    @Test
    @TestData(fileName = WRITE_WORK_CLASSIFICATION_CSV_REPORT)
    public void testWriteWorkClassificationCsvReportWithSearch() throws Exception {
        assertFilesWithExecutor(
            outputStream -> reportRepository.writeWorkClassificationCsvReport("987654321", outputStream),
            "nts/work_classification_report_by_search.csv");
    }
}
